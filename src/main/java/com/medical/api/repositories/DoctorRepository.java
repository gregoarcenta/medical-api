package com.medical.api.repositories;

import com.medical.api.models.Doctor;
import com.medical.api.utils.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByIdAndActiveTrue(Long id);

    boolean existsByIdAndActiveTrue(Long id);

    Page<Doctor> findByActiveTrue(Pageable pageable);

    @Query("""
            select count(d) from Doctor d
            where d.active = true
              and d.specialty = :specialty
              and d.id not in (
                  select a.doctor.id from Appointment a
                  where a.date between :start and :end
              )
            """)
    long countAvailableDoctors(@Param("specialty") Specialty specialty,
                               @Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end);

    @Query("""
            select d from Doctor d
            where d.active = true
              and d.specialty = :specialty
              and d.id not in (
                  select a.doctor.id from Appointment a
                  where a.date between :start and :end
              )
            """)
    Page<Doctor> findAvailableDoctors(@Param("specialty") Specialty specialty,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      Pageable pageable);
}