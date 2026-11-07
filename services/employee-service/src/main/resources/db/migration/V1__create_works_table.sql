CREATE TABLE works (
    id UUID PRIMARY KEY,
    employee_id UUID,
    position_id UUID,
    level_id UUID,
    hire_date DATE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

CREATE INDEX idx_works_employee_id ON works(employee_id);
CREATE INDEX idx_works_level_id ON works(level_id);
CREATE INDEX idx_works_position_id ON works(position_id);