package com.anwar.amenityreservationsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anwar.amenityreservationsystem.entity.Capacity;
import com.anwar.amenityreservationsystem.entity.User;
import com.anwar.amenityreservationsystem.service.CapacityService;
import com.anwar.amenityreservationsystem.service.UserService;

@RestController
@RequestMapping("/api/reservation-capacity")
public class CapacityController {

	@Autowired
	private CapacityService capacityService;
	
	@Autowired
	private UserService userService;

	public CapacityController() {
		super();
	}

	@GetMapping("/list")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public ResponseEntity<List<Capacity>> getAllCapacities() 
	{
		List<Capacity> capacityList = capacityService.getAllCapacity();
		return new ResponseEntity<List<Capacity>>(capacityList, HttpStatus.OK);
	}
	
	@GetMapping("/read/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER') OR hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Capacity> getCapacity(@PathVariable(value = "id") Long id) 
	{
		Capacity capacity = capacityService.getCapacityById(id);
		return new ResponseEntity<Capacity>(capacity, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ROLE_USER') OR hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Capacity> create(@RequestBody Capacity capacityRequest) {
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username );

        assert user != null;
        Long id = capacityService.create(capacityRequest);
        Capacity capacity = capacityService.getCapacityById(id);
        return new ResponseEntity<Capacity>(capacity, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER') OR hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Capacity> update(@PathVariable(value = "id") Long id, @RequestBody Capacity capacityRequest) {
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username );

        assert user != null;
        capacityService.updateCapacity(id, capacityRequest);
        Capacity capacity = capacityService.getCapacityById(id);
        return new ResponseEntity<Capacity>(capacity, HttpStatus.OK);
	}
	

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public ResponseEntity<String> deleteCapacityById(@PathVariable("id") long id){
		capacityService.delete(id);
		return new ResponseEntity<String>("Capacity deleted successfully!.", HttpStatus.OK);
	}
	
}
