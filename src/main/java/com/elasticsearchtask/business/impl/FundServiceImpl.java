package com.elasticsearchtask.business.impl;

import com.elasticsearchtask.business.service.FundService;
import com.elasticsearchtask.model.doc.FundDocument;
import com.elasticsearchtask.model.entity.FundEntity;
import com.elasticsearchtask.repository.elastic.FundSearchRepository;
import com.elasticsearchtask.repository.jpa.FundRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FundServiceImpl implements FundService {

    private final FundRepository fundRepository;
    private final FundSearchRepository fundSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;


    public FundServiceImpl(
            FundRepository fundRepository,
            FundSearchRepository fundSearchRepository,
            ElasticsearchOperations elasticsearchOperations
    ) {
        this.fundRepository = fundRepository;
        this.fundSearchRepository = fundSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void importFromExcel(MultipartFile file) {

        try (var workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(file.getInputStream())) {

            var sheet = workbook.getSheetAt(0);

            // header satırını atlıyoruz
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                var row = sheet.getRow(i);
                if (row == null) continue;

                FundEntity entity = new FundEntity();
                entity.setFundCode(getString(row, 0));
                entity.setFundName(getString(row, 1));
                entity.setUmbrellaType(getString(row, 2));

                entity.setReturn1M(getDouble(row, 3));
                entity.setReturn3M(getDouble(row, 4));
                entity.setReturn6M(getDouble(row, 5));
                entity.setReturnYtd(getDouble(row, 6));
                entity.setReturn1Y(getDouble(row, 7));
                entity.setReturn5Y(getDouble(row, 8));

                // ŞİMDİLİK SADECE LOGICAL FLOW
                fundRepository.save(entity);

                // Elastic mapping & save birazdan
                FundDocument document = toDocument(entity);
                fundSearchRepository.save(document);
            }

        } catch (Exception e) {
            throw new RuntimeException("Excel import sırasında hata oluştu", e);
        }
    }

    @Override
    public Page<FundDocument> search(
            String query,
            String umbrella,
            Double minReturn1Y,
            int page,
            int size,
            String sort,
            String direction
    ) {

        var pageable = PageRequest.of(page, size);

        var queryBuilder = NativeQuery.builder()
                .withQuery(qb -> qb.bool(b -> {

                    if (query != null && !query.isBlank()) {
                        b.should(s -> s.prefix(p ->
                                p.field("fundCode").value(query)
                        ));
                        b.should(s -> s.matchPhrasePrefix(m ->
                                m.field("fundName").query(query)
                        ));
                        b.minimumShouldMatch("1");
                    }

                    if (umbrella != null && !umbrella.isBlank()) {
                        b.filter(f -> f.match(m ->
                                m.field("umbrellaType").query(umbrella)
                        ));
                    }

                    if (minReturn1Y != null) {
                        b.filter(f -> f.range(r ->
                                r.number(n ->
                                        n.field("return1Y").gte(minReturn1Y)
                                )
                        ));
                    }

                    return b;
                }))
                .withPageable(pageable);

        // --- SORT (Elasticsearch native) ---
        if (sort != null && !sort.isBlank()) {
            queryBuilder.withSort(s -> s
                    .field(f -> f
                            .field(sort)
                            .order(
                                    "desc".equalsIgnoreCase(direction)
                                            ? co.elastic.clients.elasticsearch._types.SortOrder.Desc
                                            : co.elastic.clients.elasticsearch._types.SortOrder.Asc
                            )
                    )
            );
        }

        var nativeQuery = queryBuilder.build();

        SearchHits<FundDocument> hits =
                elasticsearchOperations.search(nativeQuery, FundDocument.class);

        var content = hits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(content, pageable, hits.getTotalHits());
    }

    //Mapper***
    private FundDocument toDocument(FundEntity entity) {
        FundDocument doc = new FundDocument();
        doc.setId(entity.getId().toString());
        doc.setFundCode(entity.getFundCode());
        doc.setFundName(entity.getFundName());
        doc.setUmbrellaType(entity.getUmbrellaType());
        doc.setReturn1M(entity.getReturn1M());
        doc.setReturn3M(entity.getReturn3M());
        doc.setReturn6M(entity.getReturn6M());
        doc.setReturnYtd(entity.getReturnYtd());
        doc.setReturn1Y(entity.getReturn1Y());
        doc.setReturn5Y(entity.getReturn5Y());
        return doc;
    }

    private String getString(org.apache.poi.ss.usermodel.Row row, int index) {
        var cell = row.getCell(index);
        return cell != null ? cell.toString().trim() : null;
    }

    private Double getDouble(org.apache.poi.ss.usermodel.Row row, int index) {
        var cell = row.getCell(index);
        if (cell == null) return null;

        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

}
