package org.apps.QuantityMeasurement.repository;

import org.apps.QuantityMeasurement.entity.QuantityDTO;
import org.apps.QuantityMeasurement.entity.QuantityMeasurementEntity;
import org.apps.QuantityMeasurement.exception.DatabaseException;
import org.apps.QuantityMeasurement.util.ApplicationConfig;
import org.apps.QuantityMeasurement.util.ConnectionPool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;

    private static final double EPSILON = 0.001;

    private final QuantityDTO FEET_DTO =
            new QuantityDTO(
                    1.0,
                    "FEET",
                    "LENGTH"
            );

    private final QuantityDTO INCHES_DTO =
            new QuantityDTO(
                    12.0,
                    "INCHES",
                    "LENGTH"
            );

    private final QuantityDTO KG_DTO =
            new QuantityDTO(
                    1.0,
                    "KILOGRAM",
                    "WEIGHT"
            );

    private final QuantityDTO RESULT_DTO =
            new QuantityDTO(
                    2.0,
                    "FEET",
                    "LENGTH"
            );

    @BeforeEach
    void setUp() {

        repository =
                QuantityMeasurementDatabaseRepository
                        .getInstance();
    }

    @Test
    void testSave_SingleEntity() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertDoesNotThrow(

                () -> repository.save(entity)
        );
    }

    @Test
    void testSave_MultipleEntities() {

        assertDoesNotThrow(() -> {

            repository.save(

                    new QuantityMeasurementEntity(

                            FEET_DTO,

                            INCHES_DTO,

                            "ADD",

                            RESULT_DTO
                    )
            );

            repository.save(

                    new QuantityMeasurementEntity(

                            KG_DTO,

                            null,

                            "CONVERT",

                            new QuantityDTO(

                                    1000,

                                    "GRAM",

                                    "WEIGHT"
                            )
                    )
            );

            repository.save(

                    new QuantityMeasurementEntity(

                            FEET_DTO,

                            INCHES_DTO,

                            "COMPARE",

                            Boolean.TRUE
                    )
            );

        });
    }



    @Test
    void testFindAll_NotNull() {

        List<QuantityMeasurementEntity> list =
                repository.findAll();

        assertNotNull(list);
    }

    @Test
    void testFindAll_InitiallyEmpty() {

        List<QuantityMeasurementEntity> list =
                repository.findAll();

        assertTrue(list.isEmpty());
    }

    @Test
    void testSave_ErrorEntity() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        KG_DTO,

                        "ADD",

                        "Incompatible Units",

                        true
                );

        assertDoesNotThrow(

                () -> repository.save(entity)
        );
    }

    @Test
    void testEntityOperationStored() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "COMPARE",

                        Boolean.TRUE
                );

        assertEquals(

                "COMPARE",

                entity.getOperation()
        );
    }

    @Test
    void testEntityResultStored() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertEquals(

                RESULT_DTO,

                entity.getResult()
        );
    }

    @Test
    void testErrorEntityFlag() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        KG_DTO,

                        "ADD",

                        "Error",

                        true
                );

        assertTrue(

                entity.isError()
        );
    }

    @Test
    void testErrorMessageStored() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        KG_DTO,

                        "ADD",

                        "Error Message",

                        true
                );

        assertEquals(

                "Error Message",

                entity.getErrorMessage()
        );
    }
    @Test
    void testEntityThisQuantity() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertEquals(
                FEET_DTO,
                entity.getThisQuantity()
        );
    }

    @Test
    void testEntityThatQuantity() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertEquals(
                INCHES_DTO,
                entity.getThatQuantity()
        );
    }

    @Test
    void testEntityToString_Result() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertNotNull(
                entity.toString()
        );
    }

    @Test
    void testEntityToString_Error() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        KG_DTO,

                        "ADD",

                        "Error",

                        true
                );

        assertEquals(

                "Error",

                entity.toString()
        );
    }

    @Test
    void testEntityHashCode() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertNotEquals(
                0,
                entity.hashCode()
        );
    }

    @Test
    void testEntityEquals_SameObject() {

        QuantityMeasurementEntity entity =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertEquals(
                entity,
                entity
        );
    }

    @Test
    void testEntityEquals_DifferentObject() {

        QuantityMeasurementEntity entity1 =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        QuantityMeasurementEntity entity2 =

                new QuantityMeasurementEntity(

                        FEET_DTO,

                        INCHES_DTO,

                        "ADD",

                        RESULT_DTO
                );

        assertEquals(
                entity1,
                entity2
        );
    }

    @Test
    void testDatabaseExceptionCreation() {

        DatabaseException exception =

                new DatabaseException(

                        "Database Error"
                );

        assertEquals(

                "Database Error",

                exception.getMessage()
        );
    }

    @Test
    void testDatabaseExceptionWithCause() {

        RuntimeException cause =
                new RuntimeException("SQL");

        DatabaseException exception =

                new DatabaseException(

                        "Database Error",

                        cause
                );

        assertEquals(

                cause,

                exception.getCause()
        );
    }

    @Test
    void testRepositoryInstance_NotNull() {

        assertNotNull(repository);
    }

    @Test
    void testRepositorySingleton() {

        QuantityMeasurementDatabaseRepository repository2 =

                QuantityMeasurementDatabaseRepository
                        .getInstance();

        assertSame(
                repository,
                repository2
        );
    }

    @Test
    void testFindAll_ReturnType() {

        List<QuantityMeasurementEntity> list =
                repository.findAll();

        assertTrue(

                list instanceof List
        );
    }

    @Test
    void testRepositoryHandlesMultipleFindCalls() {

        List<QuantityMeasurementEntity> first =
                repository.findAll();

        List<QuantityMeasurementEntity> second =
                repository.findAll();

        assertNotNull(first);

        assertNotNull(second);
    }

    @Test
    void testRepositoryStress_FindAll() {

        for (int i = 0; i < 100; i++) {

            assertNotNull(
                    repository.findAll()
            );
        }
    }

    @Test
    void testRepositoryStress_Save() {

        for (int i = 0; i < 20; i++) {

            QuantityMeasurementEntity entity =

                    new QuantityMeasurementEntity(

                            FEET_DTO,

                            INCHES_DTO,

                            "ADD",

                            RESULT_DTO
                    );

            assertDoesNotThrow(

                    () -> repository.save(entity)
            );
        }
    }
}