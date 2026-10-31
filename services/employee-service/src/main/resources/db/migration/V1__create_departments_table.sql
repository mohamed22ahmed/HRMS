CREATE TABLE departments (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    parent_department_id UUID,
    manager_employee_id UUID,
    is_active VARCHAR(30) NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

CREATE INDEX idx_departments_parent_department_id ON departments(parent_department_id);
CREATE INDEX idx_departments_manager_employee_id ON departments(manager_employee_id);