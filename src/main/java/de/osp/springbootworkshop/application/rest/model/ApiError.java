package de.osp.springbootworkshop.application.rest.model;

import java.util.Objects;

/**
 * @author Denny
 */
public class ApiError {
    private String message;

    public ApiError(String message) {
        this.message = message;
    }

    public static ApiError of(String message) {
        return new ApiError(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiError apiError = (ApiError) o;
        return Objects.equals(message, apiError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
