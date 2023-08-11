create table employee
(
    id              VARCHAR(36)  NOT NULL PRIMARY KEY,
    created_at      DATETIME     NOT NULL,
    modified_at     DATETIME     NOT NULL,
    name            VARCHAR(255) NOT NULL
);

create table external_employee
(
    created_at    DATETIME     NOT NULL,
    modified_at   DATETIME     NOT NULL,
    employee_id   VARCHAR(36)  NOT NULL,
    company_name  VARCHAR(255) NOT NULL,
    CONSTRAINT uq_employee_company_name UNIQUE (employee_id, company_name),
    CONSTRAINT fk_external_employee FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
);