CREATE TABLE positions (
    id UUID PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    code VARCHAR(100) NOT NULL,
    department_id UUID,
    is_active VARCHAR(5) NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_positions_department_id ON positions(department_id);
