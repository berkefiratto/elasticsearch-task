package com.elasticsearchtask.model.doc;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "fund")
public class FundDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String fundCode;

    @Field(type = FieldType.Text)
    private String fundName;

    @Field(type = FieldType.Text)
    private String umbrellaType;

    @Field(type = FieldType.Double)
    private Double return1M;

    @Field(type = FieldType.Double)
    private Double return3M;

    @Field(type = FieldType.Double)
    private Double return6M;

    @Field(type = FieldType.Double)
    private Double returnYtd;

    @Field(type = FieldType.Double)
    private Double return1Y;

    @Field(type = FieldType.Double)
    private Double return5Y;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getUmbrellaType() {
        return umbrellaType;
    }

    public void setUmbrellaType(String umbrellaType) {
        this.umbrellaType = umbrellaType;
    }

    public Double getReturn1M() {
        return return1M;
    }

    public void setReturn1M(Double return1M) {
        this.return1M = return1M;
    }

    public Double getReturn3M() {
        return return3M;
    }

    public void setReturn3M(Double return3M) {
        this.return3M = return3M;
    }

    public Double getReturn6M() {
        return return6M;
    }

    public void setReturn6M(Double return6M) {
        this.return6M = return6M;
    }

    public Double getReturnYtd() {
        return returnYtd;
    }

    public void setReturnYtd(Double returnYtd) {
        this.returnYtd = returnYtd;
    }

    public Double getReturn1Y() {
        return return1Y;
    }

    public void setReturn1Y(Double return1Y) {
        this.return1Y = return1Y;
    }

    public Double getReturn5Y() {
        return return5Y;
    }

    public void setReturn5Y(Double return5Y) {
        this.return5Y = return5Y;
    }
}