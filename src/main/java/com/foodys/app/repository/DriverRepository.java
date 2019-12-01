package com.foodys.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.Provider;
import com.foodys.app.models.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

	
	public Driver findDriverByEmail(String email);

	public Driver findDriverById(long id);
	
}
