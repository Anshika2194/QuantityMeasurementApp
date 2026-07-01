package org.apps.QuantityMeasurement.repository;

import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;
import org.apps.QuantityMeasurement.exception.DatabaseException;
import org.apps.QuantityMeasurement.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static QuantityMeasurementDatabaseRepository instance;

    private static final Logger logger =
            Logger.getLogger(
                    QuantityMeasurementDatabaseRepository.class.getName()
            );

    private final Connection connection;

    private QuantityMeasurementDatabaseRepository() {

        connection = ConnectionPool.getConnection();

        createTableIfNotExists();
    }

    private void createTableIfNotExists() {

        String query = """

                CREATE TABLE IF NOT EXISTS quantity_measurement_history (

                    id INT PRIMARY KEY AUTO_INCREMENT,

                    first_value DOUBLE NOT NULL,

                    first_unit VARCHAR(30) NOT NULL,

                    first_measurement_type VARCHAR(30) NOT NULL,

                    second_value DOUBLE,

                    second_unit VARCHAR(30),

                    second_measurement_type VARCHAR(30),

                    operation VARCHAR(30) NOT NULL,

                    result VARCHAR(255),

                    error_message VARCHAR(255),

                    is_error BOOLEAN DEFAULT FALSE

                )

                """;

        try (

                Statement statement =
                        connection.createStatement()

        ) {

            statement.execute(query);

            logger.info(
                    "Database table verified successfully."
            );

        } catch (SQLException exception) {

            logger.severe(
                    "Unable to create database table."
            );

            throw new DatabaseException(
                    "Failed to create table",
                    exception
            );
        }
    }

    public static QuantityMeasurementDatabaseRepository
    getInstance() {

        if (instance == null) {

            instance =
                    new QuantityMeasurementDatabaseRepository();
        }

        return instance;
    }

    @Override
    public void save(
            QuantityMeasurementEntity entity
    ) {

        String query = """

                INSERT INTO quantity_measurement_history (

                    first_value,

                    first_unit,

                    first_measurement_type,

                    second_value,

                    second_unit,

                    second_measurement_type,

                    operation,

                    result,

                    error_message,

                    is_error

                )

                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

                """;

        try (

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query)

        ) {

            preparedStatement.setDouble(
                    1,
                    entity.getThisQuantity().getValue()
            );

            preparedStatement.setString(
                    2,
                    entity.getThisQuantity().getUnit()
            );

            preparedStatement.setString(
                    3,
                    entity.getThisQuantity().getMeasurementType()
            );

            preparedStatement.setDouble(
                    4,
                    entity.getThatQuantity().getValue()
            );

            preparedStatement.setString(
                    5,
                    entity.getThatQuantity().getUnit()
            );

            preparedStatement.setString(
                    6,
                    entity.getThatQuantity().getMeasurementType()
            );

            preparedStatement.setString(
                    7,
                    entity.getOperation()
            );

            preparedStatement.setString(
                    8,
                    entity.getResult() == null
                            ? null
                            : entity.getResult().toString()
            );

            preparedStatement.setString(
                    9,
                    entity.getErrorMessage()
            );

            preparedStatement.setBoolean(
                    10,
                    entity.isError()
            );

            preparedStatement.executeUpdate();

            logger.info(
                    "Measurement saved successfully."
            );

        } catch (SQLException exception) {

            logger.severe(
                    "Unable to save measurement."
            );

            throw new DatabaseException(
                    "Failed to save measurement",
                    exception
            );
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {

        List<QuantityMeasurementEntity> history =
                new ArrayList<>();

        String query =
                "SELECT * FROM quantity_measurement_history";

        try (

                Statement statement =
                        connection.createStatement();

                ResultSet resultSet =
                        statement.executeQuery(query)

        ) {

            while (resultSet.next()) {

                QuantityDTO firstQuantity =
                        new QuantityDTO(

                                resultSet.getDouble(
                                        "first_value"
                                ),

                                resultSet.getString(
                                        "first_unit"
                                ),

                                resultSet.getString(
                                        "first_measurement_type"
                                )
                        );

                QuantityDTO secondQuantity =
                        new QuantityDTO(

                                resultSet.getDouble(
                                        "second_value"
                                ),

                                resultSet.getString(
                                        "second_unit"
                                ),

                                resultSet.getString(
                                        "second_measurement_type"
                                )
                        );

                QuantityMeasurementEntity entity;

                if (resultSet.getBoolean("is_error")) {

                    entity =
                            new QuantityMeasurementEntity(

                                    firstQuantity,

                                    secondQuantity,

                                    resultSet.getString(
                                            "operation"
                                    ),

                                    resultSet.getString(
                                            "error_message"
                                    ),

                                    true
                            );

                } else {

                    entity =
                            new QuantityMeasurementEntity(

                                    firstQuantity,

                                    secondQuantity,

                                    resultSet.getString(
                                            "operation"
                                    ),

                                    resultSet.getString(
                                            "result"
                                    )
                            );
                }

                entity.setId(
                        resultSet.getInt("id")
                );

                history.add(entity);
            }

            logger.info(
                    "History fetched successfully."
            );

        } catch (SQLException exception) {

            logger.severe(
                    "Unable to fetch history."
            );

            throw new DatabaseException(
                    "Failed to fetch history",
                    exception
            );
        }

        return history;
    }
}