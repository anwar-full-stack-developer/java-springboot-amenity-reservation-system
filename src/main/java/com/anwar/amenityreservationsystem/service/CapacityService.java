package com.anwar.amenityreservationsystem.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.anwar.amenityreservationsystem.entity.Capacity;
import com.anwar.amenityreservationsystem.entity.Reservation;
import com.anwar.amenityreservationsystem.exception.CapacityFullException;
import com.anwar.amenityreservationsystem.repository.CapacityRepository;

@Service
public class CapacityService {

	@Autowired
    private final CapacityRepository capacityRepository;

	public CapacityService(CapacityRepository capacityRepository) {
		super();
		this.capacityRepository = capacityRepository;
	}
	
	public List<Capacity> getAllCapacity() {
		List<Capacity> capacities =  capacityRepository.findAll();
		return capacities;
	}
	
    public Capacity getCapacityById(Long id) {
		return capacityRepository.findById(id)
		          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
    
    public Long create(Capacity capacityDes) {
//        int capacity = capacityRepository.findByAmenityType(reservation.getAmenityType()).getCapacity();

        return capacityRepository.save(capacityDes).getId();
    } 
    

    public Capacity updateCapacity(final Long id, final Capacity capacityRequest) {
        final Capacity existingCapacity = capacityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
		if (Objects.nonNull(capacityRequest.getAmenityType())) {
			existingCapacity.setAmenityType(capacityRequest.getAmenityType());
		}
		

		if (Objects.nonNull(capacityRequest.getCapacity())) {
			existingCapacity.setCapacity(capacityRequest.getCapacity());
		}
		
        capacityRepository.save(existingCapacity);
        return existingCapacity;
    }
    
    public void delete(final Long id) {
    	capacityRepository.deleteById(id);
    }
    
}
