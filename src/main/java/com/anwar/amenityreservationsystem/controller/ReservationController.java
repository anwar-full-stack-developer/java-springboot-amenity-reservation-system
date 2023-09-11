package com.anwar.amenityreservationsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anwar.amenityreservationsystem.dto.ReservationDTO;
import com.anwar.amenityreservationsystem.entity.Reservation;
import com.anwar.amenityreservationsystem.entity.User;
import com.anwar.amenityreservationsystem.service.BasicauthService;
import com.anwar.amenityreservationsystem.service.ReservationService;
import com.anwar.amenityreservationsystem.service.UserDetailsServiceImpl;
import com.anwar.amenityreservationsystem.service.UserService;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private BasicauthService basicauthService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_USER') OR hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllReservations() 
	{
		List<Reservation> revList = reservationService.findAll();	
		List<ReservationDTO> dtos = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		
		//manual mapping
		TypeMap<Reservation, ReservationDTO> typeMap = mapper.createTypeMap(Reservation.class, ReservationDTO.class);
		typeMap.addMappings(mm -> {
		    mm.map(src -> src.getUser().getId(), ReservationDTO::setUserId);
		});
		
		revList.forEach(rev -> 
		    dtos.add(mapper.map(rev, ReservationDTO.class)));
		
		return new ResponseEntity<List<ReservationDTO>>(dtos, HttpStatus.OK);
	}	
	
	

	@GetMapping("/get-reservation/{reservationId}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public Optional<ReservationDTO>  getReservationById(@PathVariable(value = "reservationId") Long id) {

		Optional<Reservation> exRes = reservationService.getReservationById(id);

		ModelMapper mapper = new ModelMapper();
		// mapping
		TypeMap<Reservation, ReservationDTO> typeMap = mapper.createTypeMap(Reservation.class, ReservationDTO.class);
		typeMap.addMappings(mm -> {
		    mm.map(src -> src.getUser().getId(), ReservationDTO::setUserId);
		});
		
		ReservationDTO rev = mapper.map(exRes, ReservationDTO.class);
		return Optional.of(rev);

	}
	
	
	@PostMapping("/add-new-reservation")
	@PreAuthorize("hasAuthority('ROLE_USER') OR hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ReservationDTO> create(@RequestBody Reservation reservation) {
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username );
        
        assert user != null;
        reservation.setUser(user);
        Long id = reservationService.create(reservation);
        Set<Reservation> userReservations = user.getReservations();
        userReservations.add(reservation);
        user.setReservations(userReservations);
        userService.update(user.getId(), user);
        
        Optional<Reservation> exRes = reservationService.getReservationById(id);

		 ModelMapper mapper = new ModelMapper();
		// mapping
		TypeMap<Reservation, ReservationDTO> typeMap = mapper.createTypeMap(Reservation.class, ReservationDTO.class);
		typeMap.addMappings(mm -> {
		    mm.map(src -> src.getUser().getId(), ReservationDTO::setUserId);
		});
		
		ReservationDTO rev = mapper.map(exRes, ReservationDTO.class);       
        
		return new ResponseEntity<ReservationDTO>(rev, HttpStatus.OK);
	}

	@RequestMapping(value="/update/{reservationId}", method=RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ROLE_USER') OR hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ReservationDTO> updateReservation(@PathVariable(value = "reservationId") Long id, @RequestBody Reservation reservation) {
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username );

        assert user != null;
        reservation.setUser(user);
        reservationService.update(id, reservation);

        
        Optional<Reservation> exRes = reservationService.getReservationById(id);

		 ModelMapper mapper = new ModelMapper();
		// mapping
		TypeMap<Reservation, ReservationDTO> typeMap = mapper.createTypeMap(Reservation.class, ReservationDTO.class);
		typeMap.addMappings(mm -> {
		    mm.map(src -> src.getUser().getId(), ReservationDTO::setUserId);
		});
		
		ReservationDTO rev = mapper.map(exRes, ReservationDTO.class);       
        
		return new ResponseEntity<ReservationDTO>(rev, HttpStatus.OK);
        
	}
	

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public ResponseEntity<String> deleteReservationById(@PathVariable("id") long id){
		reservationService.delete(id);
		
		return new ResponseEntity<String>("Reservation deleted successfully!.", HttpStatus.OK);
	}
}
