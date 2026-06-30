package org.apps.QuantityMeasurement.controller;
import java.util.logging.Logger;
import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {
    private static final Logger logger =
            Logger.getLogger(
                    QuantityMeasurementController.class.getName()
            );

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(
            IQuantityMeasurementService service
    ) {
        if (service == null) {

            throw new IllegalArgumentException(

                    "Service cannot be null"
            );
        }

        this.service = service;
    }

    public boolean performCompare(
            QuantityDTO quantity1,
            QuantityDTO quantity2
    ) {

        return service.compare(
                quantity1,
                quantity2
        );
    }

    public QuantityDTO performConvert(
            QuantityDTO quantity,
            QuantityDTO targetUnit
    ) {

        return service.convert(
                quantity,
                targetUnit
        );
    }

    public QuantityDTO performAdd(
            QuantityDTO quantity1,
            QuantityDTO quantity2
    ) {

        return service.add(
                quantity1,
                quantity2
        );
    }

    public QuantityDTO performAdd(
            QuantityDTO quantity1,
            QuantityDTO quantity2,
            QuantityDTO targetUnit
    ) {

        return service.add(
                quantity1,
                quantity2,
                targetUnit
        );
    }

    public QuantityDTO performSubtract(
            QuantityDTO quantity1,
            QuantityDTO quantity2
    ) {

        return service.subtract(
                quantity1,
                quantity2
        );
    }

    public QuantityDTO performSubtract(
            QuantityDTO quantity1,
            QuantityDTO quantity2,
            QuantityDTO targetUnit
    ) {

        return service.subtract(
                quantity1,
                quantity2,
                targetUnit
        );
    }

    public double performDivide(
            QuantityDTO quantity1,
            QuantityDTO quantity2
    ) {

        return service.divide(
                quantity1,
                quantity2
        );
    }

    public void displayResult(
            Object result
    ) {

        System.out.println(result);
    }
}