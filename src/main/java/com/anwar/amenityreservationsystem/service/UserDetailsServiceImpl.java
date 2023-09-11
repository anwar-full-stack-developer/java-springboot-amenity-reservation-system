package com.anwar.amenityreservationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anwar.amenityreservationsystem.entity.User;
import com.anwar.amenityreservationsystem.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
    

	@Autowired
	private PasswordEncoder encoder;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findUserByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(
                user.getUsername()).password(user.getPassword()).roles("USER", "ADMIN").build();

        return userDetails;
    }
    
    public String addUser(User user){
	  	user.setPassword(encoder.encode(user.getPassword()));
	  	userRepository.save(user);
	  	userRepository.flush();
		return "User Added Successfully";
  }
}
