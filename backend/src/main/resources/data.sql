-- Seed Customers
INSERT INTO customers (master_id, first_name, last_name, address, city, state, zip, country, date_of_birth, ssn, tax_id, username, email, mobile_number, user_status, disable_reason, creation_date, last_login_date, customer_type)
VALUES ('MID-00000001', 'John', 'Smith', '123 Oak Street', 'Springfield', 'IL', '62704', 'US', '1985-03-15', '123-45-6789', '12-3456789', 'jsmith', 'john.smith@email.com', '555-0101', 'ACTIVE', NULL, '2024-01-15T10:30:00', '2025-05-18T14:22:00', 'INDIVIDUAL');

INSERT INTO customers (master_id, first_name, last_name, address, city, state, zip, country, date_of_birth, ssn, tax_id, username, email, mobile_number, user_status, disable_reason, creation_date, last_login_date, customer_type)
VALUES ('MID-00000002', 'Jane', 'Doe', '456 Elm Avenue', 'Chicago', 'IL', '60601', 'US', '1990-07-22', '987-65-4321', '98-7654321', 'jdoe', 'jane.doe@email.com', '555-0102', 'ACTIVE', NULL, '2024-02-20T09:15:00', '2025-05-17T11:45:00', 'INDIVIDUAL');

INSERT INTO customers (master_id, first_name, last_name, address, city, state, zip, country, date_of_birth, ssn, tax_id, username, email, mobile_number, user_status, disable_reason, creation_date, last_login_date, customer_type)
VALUES ('MID-00000003', 'Robert', 'Johnson', '789 Pine Road', 'Dallas', 'TX', '75201', 'US', '1978-11-03', '456-78-9012', '45-6789012', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'INDIVIDUAL');

INSERT INTO customers (master_id, first_name, last_name, address, city, state, zip, country, date_of_birth, ssn, tax_id, username, email, mobile_number, user_status, disable_reason, creation_date, last_login_date, customer_type)
VALUES ('MID-00000004', 'Emily', 'Williams', '321 Maple Drive', 'Los Angeles', 'CA', '90001', 'US', '1992-05-18', '321-54-6780', '32-1546780', 'ewilliams', 'emily.w@email.com', '555-0104', 'DISABLED', 'Suspicious activity detected', '2024-03-10T14:00:00', '2025-04-01T08:30:00', 'INDIVIDUAL');

INSERT INTO customers (master_id, first_name, last_name, address, city, state, zip, country, date_of_birth, ssn, tax_id, username, email, mobile_number, user_status, disable_reason, creation_date, last_login_date, customer_type)
VALUES ('MID-00000005', 'Acme', 'Corporation', '100 Business Blvd', 'New York', 'NY', '10001', 'US', '2000-01-01', '555-66-7777', '55-5667777', 'acmecorp', 'admin@acmecorp.com', '555-0105', 'ACTIVE', NULL, '2024-04-01T12:00:00', '2025-05-15T16:00:00', 'CORPORATE');

INSERT INTO customers (master_id, first_name, last_name, address, city, state, zip, country, date_of_birth, ssn, tax_id, username, email, mobile_number, user_status, disable_reason, creation_date, last_login_date, customer_type)
VALUES ('MID-00000006', 'Smith Family', 'Trust', '200 Trust Lane', 'Boston', 'MA', '02101', 'US', '2010-06-15', '888-99-0000', '88-8990000', 'smithtrust', 'trust@smithfamily.com', '555-0106', 'ACTIVE', NULL, '2024-05-05T08:00:00', '2025-05-10T10:30:00', 'TRUST');

-- Seed Policies
INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-1001', 'ACTIVE', 'OWNER', 'TERM', 'Term Life 20', '2020-01-15', true, 1);

INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-1002', 'ACTIVE', 'INSURED', 'IUL', 'Indexed Universal Life Plus', '2021-06-01', true, 1);

INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-2001', 'ACTIVE', 'OWNER', 'IUL', 'Indexed Universal Life Standard', '2022-03-15', true, 2);

INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-3001', 'PENDING', 'OWNER', 'TERM', 'Term Life 30', '2023-09-01', false, 3);

INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-4001', 'LAPSED', 'INSURED', 'TERM', 'Term Life 10', '2019-02-28', true, 4);

INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-5001', 'ACTIVE', 'OWNER', 'IUL', 'Corporate Life Plan', '2024-01-01', true, 5);

INSERT INTO policies (policy_number, policy_status, role, policy_type, product, issue_date, portal_visibility, customer_id)
VALUES ('POL-6001', 'ACTIVE', 'OWNER', 'IUL', 'Trust Life Coverage', '2023-06-15', true, 6);

-- Seed Audit Logs
INSERT INTO audit_logs (admin_username, action, target_master_id, details, timestamp)
VALUES ('admin', 'REGISTER_USER', 'MID-00000001', 'User registered with username: jsmith', '2024-01-15T10:30:00');

INSERT INTO audit_logs (admin_username, action, target_master_id, details, timestamp)
VALUES ('admin', 'DISABLE_USER', 'MID-00000004', 'User disabled. Reason: Suspicious activity detected', '2025-04-01T09:00:00');
