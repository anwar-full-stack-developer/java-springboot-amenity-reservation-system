package com.anwar.amenityreservationsystem.service;

import org.springframework.stereotype.Component;

import com.anwar.amenityreservationsystem.component.Base64Component;

@Component
public class BasicauthService {

	public String generateToken(String userName, String password) {
		String str = Base64Component.encode(userName+":"+password);
		return str;
	}
}
