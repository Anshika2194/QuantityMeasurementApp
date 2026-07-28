package org.apps.QuantityMeasurement.service;

import org.apps.QuantityMeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class QuantityMeasurementServiceImplTest {

    @MockitoBean
    private QuantityMeasurementRepository repository;

    @Test
    void serviceLoads() {

        assertNotNull(repository);
    }
}