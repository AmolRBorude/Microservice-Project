package com.jwtex.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtex.example.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	List<Address> findAllByEmpId(Long empId);

}
