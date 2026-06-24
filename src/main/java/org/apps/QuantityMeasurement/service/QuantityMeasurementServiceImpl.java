package org.apps.QuantityMeasurement.service;

import org.apps.QuantityMeasurement.*;

import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;

import org.apps.QuantityMeasurement.exception.QuantityMeasurementException;

import org.apps.QuantityMeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl

        implements IQuantityMeasurementService {

    private final
    IQuantityMeasurementRepository
            repository;

    public QuantityMeasurementServiceImpl(

            IQuantityMeasurementRepository
                    repository
    ) {

        if (repository == null) {

            throw new IllegalArgumentException(

                    "Repository cannot be null"

            );
        }

        this.repository =
                repository;
    }

    @Override

    public boolean compare(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO
    ) {

        try {

            Quantity<IMeasurable>
                    quantity1 =

                    createQuantity(

                            thisQuantityDTO
                    );

            Quantity<IMeasurable>
                    quantity2 =

                    createQuantity(

                            thatQuantityDTO
                    );

            boolean result =

                    quantity1.equals(
                            quantity2
                    );

            repository.save(

                    new QuantityMeasurementEntity(

                            thisQuantityDTO,

                            thatQuantityDTO,

                            "COMPARE",

                            result
                    )
            );

            return result;

        } catch (

                RuntimeException exception
        ) {

            throw saveAndCreateException(

                    thisQuantityDTO,

                    thatQuantityDTO,

                    "COMPARE",

                    exception
            );
        }
    }

    @Override

    public QuantityDTO convert(

            QuantityDTO thisQuantityDTO,

            QuantityDTO targetUnitDTO
    ) {

        try {

            Quantity<IMeasurable>
                    quantity =

                    createQuantity(

                            thisQuantityDTO
                    );

            IMeasurable targetUnit =

                    getUnit(

                            targetUnitDTO
                    );

            Quantity<IMeasurable>
                    result =

                    quantity.convertTo(

                            targetUnit
                    );

            QuantityDTO dto =

                    toDTO(
                            result
                    );

            repository.save(

                    new QuantityMeasurementEntity(

                            thisQuantityDTO,

                            targetUnitDTO,

                            "CONVERT",

                            dto
                    )
            );

            return dto;

        } catch (

                RuntimeException exception
        ) {

            throw saveAndCreateException(

                    thisQuantityDTO,

                    targetUnitDTO,

                    "CONVERT",

                    exception
            );
        }
    }

    @Override

    public QuantityDTO add(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO
    ) {

        return add(

                thisQuantityDTO,

                thatQuantityDTO,

                thisQuantityDTO
        );
    }

    @Override

    public QuantityDTO add(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO,

            QuantityDTO targetUnitDTO
    ) {

        try {

            Quantity<IMeasurable>
                    quantity1 =

                    createQuantity(

                            thisQuantityDTO
                    );

            Quantity<IMeasurable>
                    quantity2 =

                    createQuantity(

                            thatQuantityDTO
                    );

            IMeasurable targetUnit =

                    getUnit(

                            targetUnitDTO
                    );

            Quantity<IMeasurable>
                    result =

                    quantity1.add(

                            quantity2,

                            targetUnit
                    );

            QuantityDTO dto =

                    toDTO(
                            result
                    );

            repository.save(

                    new QuantityMeasurementEntity(

                            thisQuantityDTO,

                            thatQuantityDTO,

                            "ADD",

                            dto
                    )
            );

            return dto;

        } catch (

                RuntimeException exception
        ) {

            throw saveAndCreateException(

                    thisQuantityDTO,

                    thatQuantityDTO,

                    "ADD",

                    exception
            );
        }
    }

    @Override

    public QuantityDTO subtract(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO
    ) {

        return subtract(

                thisQuantityDTO,

                thatQuantityDTO,

                thisQuantityDTO
        );
    }

    @Override

    public QuantityDTO subtract(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO,

            QuantityDTO targetUnitDTO
    ) {

        try {

            Quantity<IMeasurable>
                    quantity1 =

                    createQuantity(

                            thisQuantityDTO
                    );

            Quantity<IMeasurable>
                    quantity2 =

                    createQuantity(

                            thatQuantityDTO
                    );

            IMeasurable targetUnit =

                    getUnit(

                            targetUnitDTO
                    );

            Quantity<IMeasurable>
                    result =

                    quantity1.subtract(

                            quantity2,

                            targetUnit
                    );

            QuantityDTO dto =

                    toDTO(
                            result
                    );

            repository.save(

                    new QuantityMeasurementEntity(

                            thisQuantityDTO,

                            thatQuantityDTO,

                            "SUBTRACT",

                            dto
                    )
            );

            return dto;

        } catch (

                RuntimeException exception
        ) {

            throw saveAndCreateException(

                    thisQuantityDTO,

                    thatQuantityDTO,

                    "SUBTRACT",

                    exception
            );
        }
    }

    @Override

    public double divide(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO
    ) {

        try {

            Quantity<IMeasurable>
                    quantity1 =

                    createQuantity(

                            thisQuantityDTO
                    );

            Quantity<IMeasurable>
                    quantity2 =

                    createQuantity(

                            thatQuantityDTO
                    );

            double result =

                    quantity1.divide(
                            quantity2
                    );

            repository.save(

                    new QuantityMeasurementEntity(

                            thisQuantityDTO,

                            thatQuantityDTO,

                            "DIVIDE",

                            result
                    )
            );

            return result;

        } catch (

                RuntimeException exception
        ) {

            throw saveAndCreateException(

                    thisQuantityDTO,

                    thatQuantityDTO,

                    "DIVIDE",

                    exception
            );
        }
    }

    private Quantity<IMeasurable>

    createQuantity(

            QuantityDTO dto
    ) {

        return new Quantity<>(

                dto.getValue(),

                getUnit(dto)
        );
    }

    private IMeasurable getUnit(

            QuantityDTO dto
    ) {

        switch (

                dto.getMeasurementType()
                        .toUpperCase()
        ) {

            case "LENGTH":

                return LengthUnit.FEET

                        .getUnitInstance(

                                dto.getUnit()
                        );

            case "WEIGHT":

                return WeightUnit.KILOGRAM

                        .getUnitInstance(

                                dto.getUnit()
                        );

            case "VOLUME":

                return VolumeUnit.LITRE

                        .getUnitInstance(

                                dto.getUnit()
                        );

            case "TEMPERATURE":

                return TemperatureUnit.CELSIUS

                        .getUnitInstance(

                                dto.getUnit()
                        );

            default:

                throw new IllegalArgumentException(

                        "Invalid measurement type"

                );
        }
    }

    private QuantityDTO toDTO(

            Quantity<IMeasurable>
                    quantity
    ) {

        return new QuantityDTO(

                quantity.getValue(),

                quantity.getUnit()
                        .getUnitName(),

                quantity.getUnit()
                        .getMeasurementType()
        );
    }

    private QuantityMeasurementException

    saveAndCreateException(

            QuantityDTO thisQuantityDTO,

            QuantityDTO thatQuantityDTO,

            String operation,

            RuntimeException exception
    ) {

        repository.save(

                new QuantityMeasurementEntity(

                        thisQuantityDTO,

                        thatQuantityDTO,

                        operation,

                        exception.getMessage(),

                        true
                )
        );

        return new QuantityMeasurementException(

                exception.getMessage(),

                exception
        );
    }
}