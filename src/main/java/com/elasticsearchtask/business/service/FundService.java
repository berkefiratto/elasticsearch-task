package com.elasticsearchtask.business.service;

import com.elasticsearchtask.model.doc.FundDocument;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface FundService {

    void importFromExcel(MultipartFile file);

    Page<FundDocument> search(String query, String umbrella, Double minReturn1Y, int page, int size, String sort, String direction
    );
}
