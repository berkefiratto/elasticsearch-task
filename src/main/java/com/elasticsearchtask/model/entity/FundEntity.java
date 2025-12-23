package com.elasticsearchtask.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fund")
public class FundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fund_code", nullable = false)
    private String fundCode;

    @Column(name = "fund_name", nullable = false)
    private String fundName;

    @Column(name = "umbrella_type")
    private String umbrellaType;

    @Column(name = "return_1m")
    private Double return1M;

    @Column(name = "return_3m")
    private Double return3M;

    @Column(name = "return_6m")
    private Double return6M;

    @Column(name = "return_ytd")
    private Double returnYtd;

    @Column(name = "return_1y")
    private Double return1Y;

    @Column(name = "return_5y")
    private Double return5Y;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ##

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
