package com.anwar.amenityreservationsystem.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.anwar.amenityreservationsystem.entity.Reservation;
import com.anwar.amenityreservationsystem.exception.CapacityFullException;
import com.anwar.amenityreservationsystem.repository.CapacityRepository;
import com.anwar.amenityreservationsystem.repository.ReservationRepository;


@Service
public class ReservationService {
	@Autowired
    private final ReservationRepository reservationRepository;
	
	@Autowired
    private final CapacityRepository capacityRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final CapacityRepository capacityRepository) {
        this.reservationRepository = reservationRepository;
        this.capacityRepository = capacityRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
//        return reservationRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return reservationRepository.findById(id);
    }

    public Long create(final Reservation reservation) {
        int capacity = capacityRepository.findByAmenityType(reservation.getAmenityType()).getCapacity();
        int overlappingReservations = reservationRepository
                .findReservationsByReservationDateAndStartTimeBeforeAndEndTimeAfterOrStartTimeBetween(
                        reservation.getReservationDate(),
                        reservation.getStartTime(), reservation.getEndTime(),
                        reservation.getStartTime(), reservation.getEndTime()).size();

        if (overlappingReservations >= capacity) {
            throw new CapacityFullException("This amenity's capacity is full at desired time");
        }

        return reservationRepository.save(reservation).getId();
    }

    public void update(final Long id, final Reservation reservationDetails) {
        final Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
		if (Objects.nonNull(reservationDetails.getReservationDate())) {
			existingReservation.setReservationDate(reservationDetails.getReservationDate());
		}
		

		if (Objects.nonNull(reservationDetails.getStartTime())) {
			existingReservation.setStartTime(reservationDetails.getStartTime());
		}
		
		if (Objects.nonNull(reservationDetails.getEndTime())) {
			existingReservation.setEndTime(reservationDetails.getEndTime());
		}
		
		if (Objects.nonNull(reservationDetails.getAmenityType())) {
			existingReservation.setAmenityType(reservationDetails.getAmenityType());
		}
		
		
        reservationRepository.save(existingReservation);
    }

    public void delete(final Long id) {
        reservationRepository.deleteById(id);
    }

}