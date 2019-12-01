package com.foodys.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.Provider;
import com.foodys.app.models.User;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

	
	public Provider findProviderByEmail(String email);

	public Provider findProviderById(long id);
	
}
