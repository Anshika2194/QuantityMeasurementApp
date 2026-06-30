package org.apps.QuantityMeasurement.repository;

import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(
            QuantityMeasurementEntity entity
    );

    List<QuantityMeasurementEntity> findAll();

}