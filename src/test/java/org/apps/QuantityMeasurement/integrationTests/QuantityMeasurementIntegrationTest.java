package org.apps.QuantityMeasurement.integration;

import org.apps.QuantityMeasurement.controller.QuantityMeasurementController;
import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;
import org.apps.QuantityMeasurement.exception.QuantityMeasurementException;
import org.apps.QuantityMeasurement.repository.IQuantityMeasurementRepository;
import org.apps.QuantityMeasurement.repository.QuantityMeasurementDatabaseRepository;
import org.apps.QuantityMeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementIntegrationTest {

    private IQuantityMeasurementRepository repository;

    private QuantityMeasurementController controller;

    private static final double EPSILON = 0.001;

    @BeforeEach
    void setUp() {

        repository =
                QuantityMeasurementDatabaseRepository
                        .getInstance();

        controller =
                new QuantityMeasurementController(

                        new QuantityMeasurementServiceImpl(
                                repository
                        )
                );
    }

    @Test
    void testIntegration_Compare_Length() {

        boolean result =
                controller.performCompare(

                        new QuantityDTO(
                                1.0,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                12.0,
                                "INCHES",
                                "LENGTH"
                        )
                );

        assertTrue(result);
    }

    @Test
    void testIntegration_Compare_DifferentCategories() {

        boolean result =
                controller.performCompare(

                        new QuantityDTO(
                                1.0,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                1.0,
                                "KILOGRAM",
                                "WEIGHT"
                        )
                );

        assertFalse(result);
    }

    @Test
    void testIntegration_Convert_Length() {

        QuantityDTO result =
                controller.performConvert(

                        new QuantityDTO(
                                1.0,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                0,
                                "INCHES",
                                "LENGTH"
                        )
                );

        assertEquals(
                12.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testIntegration_Convert_Temperature() {

        QuantityDTO result =
                controller.performConvert(

                        new QuantityDTO(
                                100,
                                "CELSIUS",
                                "TEMPERATURE"
                        ),

                        new QuantityDTO(
                                0,
                                "FAHRENHEIT",
                                "TEMPERATURE"
                        )
                );

        assertEquals(
                212.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testIntegration_Add_Length() {

        QuantityDTO result =
                controller.performAdd(

                        new QuantityDTO(
                                1,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                12,
                                "INCHES",
                                "LENGTH"
                        )
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testIntegration_Add_Weight() {

        QuantityDTO result =
                controller.performAdd(

                        new QuantityDTO(
                                1,
                                "KILOGRAM",
                                "WEIGHT"
                        ),

                        new QuantityDTO(
                                1000,
                                "GRAM",
                                "WEIGHT"
                        )
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testIntegration_Subtract_Weight() {

        QuantityDTO result =
                controller.performSubtract(

                        new QuantityDTO(
                                5,
                                "KILOGRAM",
                                "WEIGHT"
                        ),

                        new QuantityDTO(
                                2,
                                "KILOGRAM",
                                "WEIGHT"
                        )
                );

        assertEquals(
                3.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testIntegration_Divide_Length() {

        double result =
                controller.performDivide(

                        new QuantityDTO(
                                10,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                2,
                                "FEET",
                                "LENGTH"
                        )
                );

        assertEquals(
                5.0,
                result,
                EPSILON
        );
    }

    @Test
    void testIntegration_Divide_ByZero() {

        assertThrows(

                QuantityMeasurementException.class,

                () -> controller.performDivide(

                        new QuantityDTO(
                                10,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                0,
                                "FEET",
                                "LENGTH"
                        )
                )
        );
    }



    @Test
    void testIntegration_Repository_SaveOperation() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        new QuantityDTO(
                                1,
                                "FEET",
                                "LENGTH"
                        ),

                        new QuantityDTO(
                                12,
                                "INCHES",
                                "LENGTH"
                        ),

                        "COMPARE",

                        Boolean.TRUE
                );

        repository.save(entity);

        List<QuantityMeasurementEntity> list =
                repository.findAll();

        assertNotNull(list);
    }
}