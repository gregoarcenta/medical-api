package com.medical.api.validations;

import com.medical.api.dto.AppointmentRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AppointmentDurationValidator implements IValidator {

    public void validate(AppointmentRequest appointmentRequest) {
        var date = appointmentRequest.date();
        var currentTime = LocalDateTime.now();
        var difference = Duration.between(currentTime, date).toMinutes();
        if (difference < 30){
            throw new IllegalArgumentException("Horario seleccionado con menos de 30 minutos de anticipación.");
        }
    }

}
