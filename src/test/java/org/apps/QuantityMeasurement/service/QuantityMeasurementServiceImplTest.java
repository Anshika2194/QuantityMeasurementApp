package org.apps.QuantityMeasurement.service;

import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.repository.QuantityMeasurementCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceImplTest {

    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setUp() {

        QuantityMeasurementCacheRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        service =
                new QuantityMeasurementServiceImpl(
                        repository
                );
    }

    @Test
    void shouldCompareLengthSuccessfully() {

        QuantityDTO feet =
                new QuantityDTO(
                        1,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12,
                        "INCHES",
                        "LENGTH"
                );

        assertTrue(
                service.compare(
                        feet,
                        inches
                )
        );
    }

    @Test
    void shouldConvertLength() {

        QuantityDTO feet =
                new QuantityDTO(
                        1,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO target =
                new QuantityDTO(
                        0,
                        "INCHES",
                        "LENGTH"
                );

        QuantityDTO result =
                service.convert(
                        feet,
                        target
                );

        assertEquals(
                "INCHES",
                result.getUnit()
        );

        assertEquals(
                12,
                result.getValue(),
                0.01
        );
    }

    @Test
    void shouldAddLength() {

        QuantityDTO feet =
                new QuantityDTO(
                        1,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12,
                        "INCHES",
                        "LENGTH"
                );

        QuantityDTO result =
                service.add(
                        feet,
                        inches
                );

        assertEquals(
                2,
                result.getValue(),
                0.01
        );
    }

    @Test
    void shouldSubtractLength() {

        QuantityDTO feet =
                new QuantityDTO(
                        10,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO inches =
                new QuantityDTO(
                        6,
                        "INCHES",
                        "LENGTH"
                );

        QuantityDTO result =
                service.subtract(
                        feet,
                        inches
                );

        assertEquals(
                9.5,
                result.getValue(),
                0.01
        );
    }

    @Test
    void shouldDivideLength() {

        QuantityDTO first =
                new QuantityDTO(
                        10,
                        "FEET",
                        "LENGTH"
                );

        QuantityDTO second =
                new QuantityDTO(
                        2,
                        "FEET",
                        "LENGTH"
                );

        assertEquals(
                5,
                service.divide(
                        first,
                        second
                ),
                0.01
        );
    }

    @Test
    void shouldCompareWeightSuccessfully() {

        QuantityDTO kilogram =
                new QuantityDTO(
                        1,
                        "KILOGRAM",
                        "WEIGHT"
                );

        QuantityDTO gram =
                new QuantityDTO(
                        1000,
                        "GRAM",
                        "WEIGHT"
                );

        assertTrue(
                service.compare(
                        kilogram,
                        gram
                )
        );
    }

    @Test
    void shouldCompareVolumeSuccessfully() {

        QuantityDTO litre =
                new QuantityDTO(
                        1,
                        "LITRE",
                        "VOLUME"
                );

        QuantityDTO millilitre =
                new QuantityDTO(
                        1000,
                        "MILLILITRE",
                        "VOLUME"
                );

        assertTrue(
                service.compare(
                        litre,
                        millilitre
                )
        );
    }

    @Test
    void shouldConvertTemperature() {

        QuantityDTO celsius =
                new QuantityDTO(
                        0,
                        "CELSIUS",
                        "TEMPERATURE"
                );

        QuantityDTO fahrenheit =
                new QuantityDTO(
                        0,
                        "FAHRENHEIT",
                        "TEMPERATURE"
                );

        QuantityDTO result =
                service.convert(
                        celsius,
                        fahrenheit
                );

        assertEquals(
                32,
                result.getValue(),
                0.01
        );
    }




}