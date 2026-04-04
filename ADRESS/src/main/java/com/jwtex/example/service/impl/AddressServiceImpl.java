package com.jwtex.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jwtex.example.exception.ResourceNotFoundException;
import com.jwtex.example.model.dto.AddressDto;
import com.jwtex.example.model.dto.AddressRequest;
import com.jwtex.example.model.dto.AddressRequestDto;
import com.jwtex.example.model.entity.Address;
import com.jwtex.example.repository.AddressRepository;
import com.jwtex.example.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AddressServiceImpl implements AddressService{
	
	Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	private final AddressRepository addressRepository;
	private final ModelMapper modelMapper;

	public AddressServiceImpl(AddressRepository addressRepository,
            ModelMapper modelMapper) 
	{
			this.addressRepository = addressRepository;
			this.modelMapper = modelMapper;  
    }

	@Override
	public List<AddressDto> saveAddress(AddressRequest addressRequest) 
	{
		
	    List<Address> listToSave = this.saveOrUpdateAddressRequest(addressRequest);
		List<Address> savedAddress = addressRepository.saveAll(listToSave);
		return savedAddress.stream().map(address -> modelMapper.map(address,AddressDto.class)).toList();
		
	}

	@Override
	public List<AddressDto> updateAddress(AddressRequest addressRequest) 
	{
	    List<Address> addressByEmpId = addressRepository.findAllByEmpId(addressRequest.getEmpId());	
		
	    if(addressByEmpId.isEmpty())
	    {
	    	log.info("No address found for employee id {}",addressRequest.getEmpId());
	    	log.info("Creating new address for employee id {}",addressRequest.getEmpId());
	    }
	    
	    List<Address> listToUpdate = this.saveOrUpdateAddressRequest(addressRequest);
	    
	    
	    List<Long> upcomingNonNullIds = listToUpdate.stream().map(Address::getId).filter(Objects::nonNull).toList();
	    List<Long> existingIds = addressByEmpId.stream().map(Address::getId).toList();
	   
	    List<Long> IdsToDelete = existingIds.stream().filter(id -> !upcomingNonNullIds.contains(id)).toList();
	    
	    if(!IdsToDelete.isEmpty())
	    {
	    	addressRepository.deleteAllById(IdsToDelete);
	    }
	    
	    List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);
	    
	    return updatedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
	}

	@Override
	public AddressDto getSingleAddress(Long id) 
	{
		
		 Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id "+id, HttpStatus.NOT_FOUND));
		 return modelMapper.map(address, AddressDto.class);
	}

	@Override
	public List<AddressDto> getAllAddress() 
	{
	    List<Address> listOfAddress = addressRepository.findAll();

	    if(listOfAddress.isEmpty())
	    {
	        throw new ResourceNotFoundException("No address found", HttpStatus.NOT_FOUND);
	    }

	    return listOfAddress.stream()
	            .map(address -> modelMapper.map(address, AddressDto.class)) // ✅ FIXED
	            .toList();
	}

	@Override
	public void deleteAddress(Long id) 
	{
	    if(!addressRepository.existsById(id))
	    {
	        throw new ResourceNotFoundException("Address not found with id " + id, HttpStatus.NOT_FOUND);
	    }
	    
	    addressRepository.deleteById(id);
	}

	
	private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest)
	{
		List<Address> listToSave = new ArrayList<>();
	
			for(AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList())
			{
				Address address = new Address();
				address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
				address.setStreet(addressRequestDto.getStreet());
				address.setCity(addressRequestDto.getCity());
				address.setCountry(addressRequestDto.getCountry());
				address.setPinCode(addressRequestDto.getPinCode());
				address.setAddressType(addressRequestDto.getAddressType());
				address.setEmpId(addressRequest.getEmpId());
				
				listToSave.add(address);
			}
		return listToSave;
	}


}
