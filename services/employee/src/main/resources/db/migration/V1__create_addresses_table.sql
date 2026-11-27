CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    tenant_id UUID,
    country	VARCHAR(100),
    state VARCHAR(100),
    city VARCHAR(100),
    location TEXT,
    postal_code	VARCHAR(20),
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
