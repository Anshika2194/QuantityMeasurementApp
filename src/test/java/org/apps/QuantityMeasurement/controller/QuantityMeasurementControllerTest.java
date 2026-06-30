package org.apps.QuantityMeasurement.controller;

import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.exception.QuantityMeasurementException;
import org.apps.QuantityMeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuantityMeasurementControllerTest {

    private IQuantityMeasurementService service;

    private QuantityMeasurementController controller;

    private static final double EPSILON = 0.0001;

    private QuantityDTO feet;
    private QuantityDTO inches;
    private QuantityDTO kilogram;
    private QuantityDTO gram;

    @BeforeEach
    void setUp() {

        service = mock(IQuantityMeasurementService.class);

        controller =
                new QuantityMeasurementController(
                        service
                );

        feet =
                new QuantityDTO(
                        1.0,
                        "FEET",
                        "LENGTH"
                );

        inches =
                new QuantityDTO(
                        12.0,
                        "INCHES",
                        "LENGTH"
                );

        kilogram =
                new QuantityDTO(
                        1.0,
                        "KILOGRAM",
                        "WEIGHT"
                );

        gram =
                new QuantityDTO(
                        1000.0,
                        "GRAM",
                        "WEIGHT"
                );
    }

    @Test
    void testConstructor_NullService() {

        assertThrows(

                IllegalArgumentException.class,

                () -> new QuantityMeasurementController(
                        null
                )
        );
    }

    @Test
    void testPerformCompare_ReturnsTrue() {

        when(
                service.compare(
                        feet,
                        inches
                )
        ).thenReturn(true);

        assertTrue(

                controller.performCompare(
                        feet,
                        inches
                )
        );

        verify(service).compare(
                feet,
                inches
        );
    }

    @Test
    void testPerformCompare_ReturnsFalse() {

        when(
                service.compare(
                        feet,
                        inches
                )
        ).thenReturn(false);

        assertFalse(

                controller.performCompare(
                        feet,
                        inches
                )
        );
    }

    @Test
    void testPerformCompare_Exception() {

        when(
                service.compare(
                        any(),
                        any()
                )
        ).thenThrow(

                new QuantityMeasurementException(
                        "Comparison Error"
                )
        );

        assertThrows(

                QuantityMeasurementException.class,

                () -> controller.performCompare(
                        feet,
                        inches
                )
        );
    }

    @Test
    void testPerformConvert() {

        QuantityDTO target =

                new QuantityDTO(
                        0,
                        "INCHES",
                        "LENGTH"
                );

        QuantityDTO expected =

                new QuantityDTO(
                        12,
                        "INCHES",
                        "LENGTH"
                );

        when(
                service.convert(
                        feet,
                        target
                )
        ).thenReturn(expected);

        QuantityDTO result =

                controller.performConvert(
                        feet,
                        target
                );

        assertEquals(
                12,
                result.getValue(),
                EPSILON
        );

        verify(service).convert(
                feet,
                target
        );
    }

    @Test
    void testPerformAdd() {

        QuantityDTO expected =

                new QuantityDTO(
                        2,
                        "KILOGRAM",
                        "WEIGHT"
                );

        when(
                service.add(
                        kilogram,
                        gram
                )
        ).thenReturn(expected);

        QuantityDTO result =

                controller.performAdd(
                        kilogram,
                        gram
                );

        assertEquals(
                2,
                result.getValue(),
                EPSILON
        );

        verify(service).add(
                kilogram,
                gram
        );
    }

    @Test
    void testPerformAdd_TargetUnit() {

        QuantityDTO target =

                new QuantityDTO(
                        0,
                        "GRAM",
                        "WEIGHT"
                );

        QuantityDTO expected =

                new QuantityDTO(
                        2000,
                        "GRAM",
                        "WEIGHT"
                );

        when(
                service.add(
                        kilogram,
                        gram,
                        target
                )
        ).thenReturn(expected);

        QuantityDTO result =

                controller.performAdd(
                        kilogram,
                        gram,
                        target
                );

        assertEquals(
                2000,
                result.getValue(),
                EPSILON
        );
    }



    @Test
    void testPerformDivide() {

        when(
                service.divide(
                        feet,
                        feet
                )
        ).thenReturn(1.0);

        assertEquals(

                1.0,

                controller.performDivide(
                        feet,
                        feet
                ),

                EPSILON
        );

        verify(service).divide(
                feet,
                feet
        );
    }

    @Test
    void testPerformDivide_Exception() {

        when(
                service.divide(
                        any(),
                        any()
                )
        ).thenThrow(

                new QuantityMeasurementException(
                        "Divide by Zero"
                )
        );

        assertThrows(

                QuantityMeasurementException.class,

                () -> controller.performDivide(
                        feet,
                        new QuantityDTO(
                                0,
                                "FEET",
                                "LENGTH"
                        )
                )
        );
    }

    @Test
    void testDisplayResult() {

        assertDoesNotThrow(

                () -> controller.displayResult(
                        "Hello"
                )
        );
    }

    @Test
    void testServiceDelegationCount() {

        when(
                service.compare(
                        any(),
                        any()
                )
        ).thenReturn(true);

        controller.performCompare(
                feet,
                inches
        );

        controller.performCompare(
                feet,
                inches
        );

        verify(
                service,
                times(2)
        ).compare(
                any(),
                any()
        );
    }
}