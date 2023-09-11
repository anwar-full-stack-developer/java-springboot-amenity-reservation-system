package com.anwar.amenityreservationsystem.dto;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.anwar.amenityreservationsystem.entity.AmenityType;
import com.anwar.amenityreservationsystem.entity.User;

import jakarta.persistence.*;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private LocalDate reservationDate;

    private LocalTime startTime;

    private LocalTime endTime;


    private OffsetDateTime dateCreated;

    private OffsetDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    private AmenityType amenityType;


	//  private User user;
	  private Long userId;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * @return the reservationDate
	 */
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	
	
	/**
	 * @param reservationDate the reservationDate to set
	 */
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	
	/**
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	
	
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	
	/**
	 * @return the endTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	
	
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	
	/**
	 * @return the dateCreated
	 */
	public OffsetDateTime getDateCreated() {
		return dateCreated;
	}
	
	
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(OffsetDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	/**
	 * @return the lastUpdated
	 */
	public OffsetDateTime getLastUpdated() {
		return lastUpdated;
	}
	
	
	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(OffsetDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	/**
	 * @return the amenityType
	 */
	public AmenityType getAmenityType() {
		return amenityType;
	}
	
	
	/**
	 * @param amenityType the amenityType to set
	 */
	public void setAmenityType(AmenityType amenityType) {
		this.amenityType = amenityType;
	}
	
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	    
	//    public ReservationDTO(LocalDate reservationDate, LocalTime startTime,
	//                       LocalTime endTime, User user, AmenityType amenityType) {
	//        this.reservationDate = reservationDate;
	//        this.startTime = startTime;
	//        this.endTime = endTime;
	//        this.user = user;
	//        this.amenityType = amenityType;
	//    }
	

	
    
}
