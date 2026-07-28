CREATE TABLE IF NOT EXISTS quantity_measurement_history (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    this_value DOUBLE NOT NULL,

    this_unit VARCHAR(30) NOT NULL,

    this_measurement_type VARCHAR(30) NOT NULL,

    that_value DOUBLE NOT NULL,

    that_unit VARCHAR(30) NOT NULL,

    that_measurement_type VARCHAR(30) NOT NULL,

    operation VARCHAR(30) NOT NULL,

    result_value DOUBLE,

    result_unit VARCHAR(30),

    result_measurement_type VARCHAR(30),

    result_string VARCHAR(255),

    error_message VARCHAR(255),

    is_error BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP
);