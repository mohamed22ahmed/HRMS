CREATE TABLE educations (
    id UUID PRIMARY KEY,
    employee_id UUID,
    university VARCHAR(100),
    degree VARCHAR(100),
    department VARCHAR(100),
    graduate_date DATE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

CREATE INDEX idx_educations_employee_id ON educations(employee_id);