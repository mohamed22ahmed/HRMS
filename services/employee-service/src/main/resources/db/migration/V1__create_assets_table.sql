CREATE TABLE assets (
    id UUID PRIMARY KEY,
    employee_id UUID,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(100),
    description VARCHAR(255),
    category VARCHAR(100), /*will change to category_id,*/
    brand VARCHAR(100),
    model VARCHAR(100),
    serial_number VARCHAR(150),
    purchase_date DATE,
    purchase_cost DECIMAL(12,2),
    currency VARCHAR(3),
    condition VARCHAR(30) NOT NULL DEFAULT 'NEW',
    status VARCHAR(30)	NOT NULL DEFAULT 'AVAILABLE',
    notes VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_condition CHECK (
        condition IN ('NEW', 'GOOD', 'FAIR', 'DAMAGED')
    ),

    CONSTRAINT chk_status CHECK (
        status IN ('AVAILABLE', 'ASSIGNED', 'MAINTENANCE', 'RETIRED', 'LOST')
    )
);

CREATE INDEX idx_assets_employee_id ON assets(employee_id);
CREATE INDEX idx_assets_condition ON assets(condition);
CREATE INDEX idx_assets_status ON assets(status);
