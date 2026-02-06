package com.medical.api.validations;

import com.medical.api.dto.AppointmentRequest;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicHoursValidator implements IValidator {

    public void validate(AppointmentRequest appointmentRequest) {
        var date = appointmentRequest.date();
        var sunday = date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpeningSchedule = date.getHour() < 7;
        var afterClosingSchedule = date.getHour() > 18;

        if (sunday || beforeOpeningSchedule || afterClosingSchedule) {
            throw new IllegalArgumentException("El horario seleccionado está fuera de nuestra jornada de atención.");
        }
    }
}
