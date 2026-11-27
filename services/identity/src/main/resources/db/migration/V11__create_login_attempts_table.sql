CREATE TABLE login_attempts (
    id UUID PRIMARY KEY,
    tenant_id UUID,
    user_id UUID REFERENCES users(id),
    email VARCHAR(255) NOT NULL,
    ip_address INET,
    user_agent TEXT,
    attempt_status VARCHAR(20) NOT NULL,
    failure_reason VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
