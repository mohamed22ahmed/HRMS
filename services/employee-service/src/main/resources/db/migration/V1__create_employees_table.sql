CREATE TABLE employees (
    id UUID PRIMARY KEY,
    employee_number VARCHAR(30) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(30),
    department_id UUID,
    job_title VARCHAR(150),
    hire_date DATE NOT NULL,
    employment_status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_employee_status
        CHECK (
            employment_status IN (
                'ACTIVE',
                'INACTIVE',
                'ON_LEAVE',
                'TERMINATED'
            )
        )
);

CREATE INDEX idx_employees_department_id ON employees(department_id);
CREATE INDEX idx_employees_status ON employees(employment_status);