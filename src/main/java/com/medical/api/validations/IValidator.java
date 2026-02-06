package com.medical.api.validations;

import com.medical.api.dto.AppointmentRequest;

public interface IValidator {
    void validate(AppointmentRequest appointmentRequest);
}
