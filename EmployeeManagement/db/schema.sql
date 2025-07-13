CREATE TABLE departments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    creation_date DATE,
    head_id BIGINT
);

CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    salary DOUBLE,
    address VARCHAR(255),
    role VARCHAR(100),
    joining_date DATE,
    yearly_bonus_percentage DOUBLE,
    department_id BIGINT,
    reporting_manager_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (reporting_manager_id) REFERENCES employees(id)
);
