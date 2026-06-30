package org.apps.QuantityMeasurement.entity;

import java.io.Serializable;
import java.util.Objects;

public class QuantityMeasurementEntity
        implements Serializable {
    private int id;

    private static final long serialVersionUID = 1L;

    private final QuantityDTO thisQuantity;

    private final QuantityDTO thatQuantity;

    private final String operation;

    private final Object result;

    private final String errorMessage;

    private final boolean isError;

    // For arithmetic operations

    public QuantityMeasurementEntity(

            QuantityDTO thisQuantity,

            QuantityDTO thatQuantity,

            String operation,

            QuantityDTO result
    ) {

        this.thisQuantity = thisQuantity;

        this.thatQuantity = thatQuantity;

        this.operation = operation;

        this.result = result;

        this.errorMessage = null;

        this.isError = false;
    }

    // For comparison and division

    public QuantityMeasurementEntity(

            QuantityDTO thisQuantity,

            QuantityDTO thatQuantity,

            String operation,

            Object result
    ) {

        this.thisQuantity = thisQuantity;

        this.thatQuantity = thatQuantity;

        this.operation = operation;

        this.result = result;

        this.errorMessage = null;

        this.isError = false;
    }

    // For errors

    public QuantityMeasurementEntity(

            QuantityDTO thisQuantity,

            QuantityDTO thatQuantity,

            String operation,

            String errorMessage,

            boolean isError
    ) {

        this.thisQuantity = thisQuantity;

        this.thatQuantity = thatQuantity;

        this.operation = operation;

        this.result = null;

        this.errorMessage = errorMessage;

        this.isError = isError;
    }
    public int getId() {

        return id;
    }

    public void setId(
            int id
    ) {

        this.id = id;
    }
    public QuantityDTO getThisQuantity() {

        return thisQuantity;
    }

    public QuantityDTO getThatQuantity() {

        return thatQuantity;
    }

    public String getOperation() {

        return operation;
    }

    public Object getResult() {

        return result;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public boolean isError() {

        return isError;
    }

    @Override
    public boolean equals(
            Object obj
    ) {

        if (this == obj) {

            return true;
        }

        if (obj == null
                || getClass()
                != obj.getClass()) {

            return false;
        }

        QuantityMeasurementEntity other =

                (QuantityMeasurementEntity) obj;

        return Objects.equals(
                thisQuantity,
                other.thisQuantity
        )

                && Objects.equals(
                thatQuantity,
                other.thatQuantity
        )

                && Objects.equals(
                operation,
                other.operation
        )

                && Objects.equals(
                result,
                other.result
        )

                && Objects.equals(
                errorMessage,
                other.errorMessage
        )

                && isError
                == other.isError;
    }

    @Override
    public int hashCode() {

        return Objects.hash(

                thisQuantity,

                thatQuantity,

                operation,

                result,

                errorMessage,

                isError
        );
    }

    @Override
    public String toString() {

        return isError

                ? errorMessage

                : String.valueOf(result);
    }
}