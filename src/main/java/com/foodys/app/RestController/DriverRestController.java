package com.foodys.app.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Driver;
import com.foodys.app.repository.FoodOrderRepository;
import com.foodys.app.repository.DriverRepository;
import com.foodys.app.service.FoodOrderService;
import com.foodys.app.service.TicketService;



@RestController
public class DriverRestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private TicketService ticketService;
	
	
	@Autowired
	private DriverRepository driverRepo;
	
	@Autowired
	private FoodOrderRepository foodOrderRepo;
	
	@Autowired
	private FoodOrderService foodOrderService;

	@GetMapping("/driver/fetch/foodOrder/ticket/{id}")
	public List<FoodOrder> getMatchingFoodOrders(@PathVariable("id") long ticketId){
		logger.info("Driver fetch food Order with matching ticketId {}",ticketId);
		
		return ticketService.findFoodOrderByTicketId(ticketId);
	
	}
	
	
	@GetMapping("/driver/foodOrder/accept/{id}")
	public FoodOrder acceptFoodOrder(@PathVariable("id") long foodOrderId){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Driver driver =  driverRepo.findDriverByEmail(auth.getName());
		
    	
    	
		FoodOrder foodOrder =	foodOrderRepo.findFoodOrderById(foodOrderId);
    	foodOrder.setDriver(driver);
		logger.info("Driver->{} Accepted Food Order with Id {}",driver,foodOrderId);
		
		foodOrderService.acceptFoodOrder(foodOrder);	// creates a tracker record as well.
		
		return foodOrder;
		 
	}
	
	@GetMapping("/driver/foodOrder/collected/{id}")
	public FoodOrder collectetFoodOrder(@PathVariable("id") long foodOrderId){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Driver driver =  driverRepo.findDriverByEmail(auth.getName());
		
    	
    	
		FoodOrder foodOrder =	foodOrderRepo.findFoodOrderById(foodOrderId);
//    	foodOrder.setDriver(driver);
		logger.info("Driver->{} Collected Food Order with Id {}",driver,foodOrderId);
		
		foodOrderService.collectFoodOrder(foodOrder);	// creates a tracker record as well.
		
		return foodOrder;
		 
	}
	
	@GetMapping("/driver/foodOrder/transit/{id}")
	public FoodOrder inTransitFoodOrder(@PathVariable("id") long foodOrderId){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Driver driver =  driverRepo.findDriverByEmail(auth.getName());
		
    	
    	
		FoodOrder foordOrder =	foodOrderRepo.findFoodOrderById(foodOrderId);
		logger.info("Driver->{} In Transit for Food Order with Id {}",driver,foodOrderId);
		
		foodOrderService.transitFoodOrder(foordOrder);	// creates a tracker record as well.
		
		return foordOrder;
		 
	}
	
	@GetMapping("/driver/foodOrder/delivered/{id}")
	public FoodOrder deliveredFoodOrder(@PathVariable("id") long foodOrderId){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Driver driver =  driverRepo.findDriverByEmail(auth.getName());
		
    	
    	
		FoodOrder foodOrder =	foodOrderRepo.findFoodOrderById(foodOrderId);
    	
		logger.info("Driver->{} Accepted Food Order with Id {}",driver,foodOrderId);
		
		foodOrderService.deliverFoodOrder(foodOrder);	// creates a tracker record as well.
		
		return foodOrder;
		 
	}
	
	@GetMapping("/driver/foodOrder/returned/{id}")
	public FoodOrder returnedFoodOrder(@PathVariable("id") long foodOrderId){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Driver driver =  driverRepo.findDriverByEmail(auth.getName());
		
    	
    	
		FoodOrder foodOrder =	foodOrderRepo.findFoodOrderById(foodOrderId);
//    	foodOrder.setDriver(driver);
		logger.info("Driver->{} Accepted Food Order with Id {}",driver,foodOrderId);
		
		foodOrderService.returnFoodOrder(foodOrder);	// creates a tracker record as well.
		
		return foodOrder;
		 
	}
	
	
	
}
