package org.apps.QuantityMeasurement.repository;

import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(
            String operation
    );

    List<QuantityMeasurementEntity> findByThisMeasurementType(
            String measurementType
    );

    List<QuantityMeasurementEntity> findByIsErrorTrue();

    List<QuantityMeasurementEntity> findByCreatedAtAfter(
            LocalDateTime dateTime
    );

    long countByOperationAndIsErrorFalse(
            String operation
    );
}