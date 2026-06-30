package org.apps.QuantityMeasurement.repository;

import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static
    QuantityMeasurementDatabaseRepository
            instance;

    private QuantityMeasurementDatabaseRepository() {

    }

    public static
    QuantityMeasurementDatabaseRepository
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

        // UC16 JDBC implementation
    }

    @Override
    public List<
            QuantityMeasurementEntity
            > findAll() {

        return new ArrayList<>();
    }
}