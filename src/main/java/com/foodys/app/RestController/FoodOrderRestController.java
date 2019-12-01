package com.foodys.app.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Role;
import com.foodys.app.models.Provider;
import com.foodys.app.models.Status;
import com.foodys.app.models.Tracker;
import com.foodys.app.models.User;
import com.foodys.app.repository.ProviderRepository;
import com.foodys.app.repository.TrackerRepository;
import com.foodys.app.service.FoodOrderService;
import com.foodys.app.service.UserService;

import ch.qos.logback.core.net.SyslogOutputStream;



@RestController
public class FoodOrderRestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	FoodOrderService	foodOrderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProviderRepository providerRepo;
	
	@Autowired
	private TrackerRepository trackerRepo;

	
	
	@GetMapping("/foodOrder")
	public List<FoodOrder> getFoodOrders(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =  userService.findUserByEmail(auth.getName());
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	
    	System.out.println("*********Food Order page role is:"+roles.get(0));
    	
    	if(roles.get(0).equalsIgnoreCase("PROVIDER")) {
    		return foodOrderService.findFoodOrderByProvider_id(user.getId());
    	}else if(roles.get(0).equalsIgnoreCase("DRIVER")) {
    		return foodOrderService.findFoodOrderByDriver_id(user.getId());	
    	}else if(roles.get(0).equalsIgnoreCase("ADMIN")){
    		
    		return foodOrderService.findAll();
    	}
    	
		return null;
	}
	
	@Transactional
	@PostMapping("/foodOrder")
	public ResponseEntity<?> saveFoodOrder(@Valid @RequestBody FoodOrder foodOrder, BindingResult bindingResult) {
		String fieldName="";
		String errorMsg="";
		
		JSONObject responce = new JSONObject();
		JSONObject errors = new JSONObject();
		

		try {
		if (bindingResult.hasErrors()) {
			
			 logger.info("Food Order->{} Binding results {}",foodOrder,bindingResult.getAllErrors());
			
			 responce.put("type", "error");
			
			
			 for (Object object : bindingResult.getAllErrors()) {
				 
				    if(object instanceof FieldError) {
				        FieldError fieldError = (FieldError) object;
				        fieldName = fieldError.getField()+"Err";
				        logger.info(" Binding Codes-> {}",fieldName);
				        
				    }

				    if(object instanceof ObjectError) {
				        ObjectError objectError = (ObjectError) object;
				        errorMsg = objectError.getDefaultMessage();
				        logger.info(" Binding Errors-> {} Message {}",objectError.getCode(),errorMsg);
				    }
				    errors.put(fieldName, errorMsg);
				    
				    
				}
			 responce.put("errors", errors);
			
		}else {
		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Provider provider =  providerRepo.findProviderByEmail(auth.getName());
			foodOrder.setProvider(provider);	
			foodOrder.setStatus(Status.SUBMITTED);
			foodOrder.setId(foodOrderService.save(foodOrder));
			
			Tracker tracker = new Tracker(foodOrder,Status.SUBMITTED,"Food Order has been registered");
			trackerRepo.save(tracker);
			
			responce.put("type", "success");
			responce.put("id", foodOrder.getId());
	
		}
		
		 return ResponseEntity.ok(responce.toString());
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
		 
		 return ResponseEntity.ok("Failed parsing JSON objects");
	}
	
	@PutMapping("/foodOrder")
	public ResponseEntity<?> updateFoodOrder(@Valid @RequestBody FoodOrder foodOrder, BindingResult bindingResult){
		
		String fieldName="";
		String errorMsg="";
		
		JSONObject responce = new JSONObject();
		JSONObject errors = new JSONObject();
		

		try {
			
		if (bindingResult.hasErrors()) {
			
			 logger.info("FoodOrder->{} Binding results {}",foodOrder,bindingResult.getAllErrors());
			
			 responce.put("type", "error");
			
			
			 for (Object object : bindingResult.getAllErrors()) {
				 
				    if(object instanceof FieldError) {
				        FieldError fieldError = (FieldError) object;
				        fieldName = fieldError.getField()+"Err";
				        logger.info(" Binding Codes-> {}",fieldName);
				        
				    }

				    if(object instanceof ObjectError) {
				        ObjectError objectError = (ObjectError) object;
				        errorMsg = objectError.getDefaultMessage();
				        logger.info(" Binding Errors-> {} Message {}",objectError.getCode(),errorMsg);
				    }
				    errors.put(fieldName, errorMsg);
				    
				    
				}
			 responce.put("errors", errors);
			
		}else {
			
		foodOrder = foodOrderService.update(foodOrder);
		
			responce.put("type", "success");
		
	
		}
		
		 return ResponseEntity.ok(responce.toString());
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok("Failed parsing JSON objects");
		
	}
	
	// delete can be added here
	
	@DeleteMapping("/foodOrder/{id}")
	public ResponseEntity<?> deleteFoodOrder(@PathVariable long id){
		
		
		foodOrderService.delete(id);
		return ResponseEntity.ok(id);
		
	}
	
	@Transactional
	@PostMapping("/admin/foodOrder/approve/{id}")
	public ResponseEntity<?> approveFoodOrder(@PathVariable long id){
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user =  userService.findUserByEmail(auth.getName());
    	
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	
		if(roles.get(0).equalsIgnoreCase("ADMIN")) {
		
		
		FoodOrder foodOrder = foodOrderService.findFoodOrderById(id);
		foodOrder.setStatus(Status.APPROVED);
		foodOrderService.save(foodOrder);
		trackerRepo.save(new Tracker(foodOrder,Status.APPROVED,"Approved by Admin"));
		return ResponseEntity.ok(id);
		
		}else {
			return (ResponseEntity<?>) ResponseEntity.status(404);
		}
		
	}
	
	@Transactional
	@PostMapping("/admin/foodOrder/reject/{id}")
	public ResponseEntity<?> rejectFoodOrder(@PathVariable long id){
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user =  userService.findUserByEmail(auth.getName());
    	
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	
		if(roles.get(0).equalsIgnoreCase("ADMIN")) {
		
		
		FoodOrder foodOrder = foodOrderService.findFoodOrderById(id);
		foodOrder.setStatus(Status.REJECTED);
		foodOrderService.save(foodOrder);
		trackerRepo.save(new Tracker(foodOrder,Status.REJECTED,"Rejected by Admin"));
		return ResponseEntity.ok(id);
		
		}else {
			return (ResponseEntity<?>) ResponseEntity.status(404);
		}
		
	}
	
	
	
	
}
