package com.anwar.amenityreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anwar.amenityreservationsystem.entity.AmenityType;
import com.anwar.amenityreservationsystem.entity.Capacity;

import java.util.List;

@Repository
public interface CapacityRepository extends JpaRepository<Capacity, Long> {

    Capacity findByAmenityType(AmenityType amenityType);
}
