package com.foodys.app.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.foodys.app.models.Tracker;
import com.foodys.app.repository.FoodOrderRepository;
import com.foodys.app.repository.TrackerRepository;



@RestController
public class TrackerRestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FoodOrderRepository cRepo;
	
	@Autowired
	private TrackerRepository trackerRepo;

	@GetMapping("/tracker/foodOrder/{id}")
	public List<Tracker> getTracker(@PathVariable long id){
		logger.info("Tracker fetch foodOrder with id {}",id);
		
		return trackerRepo.findTrackerByFoodOrder(cRepo.findFoodOrderById(id));
	}
	
	
	
	
	
	
}
