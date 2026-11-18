CREATE TABLE branches (
    id UUID PRIMARY KEY,
    code VARCHAR(50) Unique,
    name VARCHAR(150),
    description	TEXT,
    email VARCHAR(150),
    phone VARCHAR(30),
    address_id UUID,
    manager_id UUID,
    is_head_office BOOLEAN,
    status VARCHAR(30),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_branch_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE INDEX idx_branches_status ON branches(status);
CREATE INDEX idx_branches_manager_id ON branches(employee_id);
CREATE INDEX idx_educations_address_id ON branches(address_id);