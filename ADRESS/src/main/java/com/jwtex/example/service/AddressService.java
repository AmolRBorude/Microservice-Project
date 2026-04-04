package com.jwtex.example.service;

import java.util.List;

import com.jwtex.example.model.dto.AddressDto;
import com.jwtex.example.model.dto.AddressRequest;

public interface AddressService {
	
	List<AddressDto> saveAddress(AddressRequest addressRequest);
	
	List<AddressDto> updateAddress(AddressRequest addressRequest);
	
	AddressDto getSingleAddress(Long id);
	
	List<AddressDto> getAllAddress();
	
	void deleteAddress(Long id);
	
	
	

}
