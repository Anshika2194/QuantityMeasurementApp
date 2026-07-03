package org.apps.QuantityMeasurement.service;

import org.apps.QuantityMeasurement.model.QuantityDTO;
import org.apps.QuantityMeasurement.model.QuantityInputDTO;
import org.apps.QuantityMeasurement.model.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    boolean compare(
            QuantityInputDTO inputDTO
    );

    QuantityDTO convert(
            QuantityInputDTO inputDTO
    );

    QuantityDTO add(
            QuantityInputDTO inputDTO
    );

    QuantityDTO subtract(
            QuantityInputDTO inputDTO
    );

    double divide(
            QuantityInputDTO inputDTO
    );

    List<QuantityMeasurementDTO> getHistory();

    List<QuantityMeasurementDTO> getHistoryByOperation(
            String operation
    );

    List<QuantityMeasurementDTO> getHistoryByMeasurementType(
            String measurementType
    );

    List<QuantityMeasurementDTO> getErroredOperations();

    long getOperationCount(
            String operation
    );
}