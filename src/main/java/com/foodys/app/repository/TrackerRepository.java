package com.foodys.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Tracker;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, Long> {

	
	public List<Tracker> findTrackerByFoodOrder(FoodOrder foodOrder);
	
	
}
