CREATE TABLE IF NOT EXISTS fund (
                                    id BIGSERIAL PRIMARY KEY,
                                    fund_code VARCHAR(50) NOT NULL,
    fund_name VARCHAR(255) NOT NULL,
    umbrella_type VARCHAR(100),

    return_1m DOUBLE PRECISION,
    return_3m DOUBLE PRECISION,
    return_6m DOUBLE PRECISION,
    return_ytd DOUBLE PRECISION,
    return_1y DOUBLE PRECISION,
    return_5y DOUBLE PRECISION,

    created_at TIMESTAMP
    );