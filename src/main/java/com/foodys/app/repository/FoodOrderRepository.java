package com.foodys.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.FoodOrder;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

	
	public List<FoodOrder> findFoodOrderByProvider_id(long provider_id);
	public List<FoodOrder> findFoodOrderByDriver_id(long driver_id);
	public FoodOrder findFoodOrderById(long id);
	public List<FoodOrder> findAll();
	
	
}
