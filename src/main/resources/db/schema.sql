CREATE TABLE IF NOT EXISTS quantity_measurement (

    id INT AUTO_INCREMENT PRIMARY KEY,

    first_value DOUBLE NOT NULL,

    first_unit VARCHAR(50) NOT NULL,

    first_measurement_type VARCHAR(50) NOT NULL,

    second_value DOUBLE,

    second_unit VARCHAR(50),

    second_measurement_type VARCHAR(50),

    operation VARCHAR(30) NOT NULL,

    result VARCHAR(100),

    error_message VARCHAR(255),

    is_error BOOLEAN NOT NULL

);

CREATE INDEX IF NOT EXISTS idx_operation
ON quantity_measurement(operation);

CREATE INDEX IF NOT EXISTS idx_measurement_type
ON quantity_measurement(first_measurement_type);