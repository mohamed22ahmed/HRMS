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

CREATE INDEX idx_works_employee_id ON works(employee_id);
CREATE INDEX idx_works_level_id ON works(level_id);
CREATE INDEX idx_works_position_id ON works(position_id);