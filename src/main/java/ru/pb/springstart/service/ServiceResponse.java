package ru.pb.springstart.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Pavel Barmyonkov on 13.10.18.
 * pbarmenkov@gmail.com
 */

public class ServiceResponse<T> {

    private String status;
    private T data;
    private Map<String, String> errorMessages;
    private boolean validated;
    private T additionalField;

    public ServiceResponse() {
    }

    public ServiceResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServiceResponse(String status, T data, T additionalField) {
        this(status,data);
        this.additionalField = additionalField;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T  getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public T getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(T additionalField) {
        this.additionalField = additionalField;
    }
}
