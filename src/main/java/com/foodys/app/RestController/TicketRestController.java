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

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Role;
import com.foodys.app.models.Provider;
import com.foodys.app.models.Status;
import com.foodys.app.models.Ticket;
import com.foodys.app.models.Tracker;
import com.foodys.app.models.Driver;
import com.foodys.app.models.User;
import com.foodys.app.repository.ProviderRepository;
import com.foodys.app.repository.TrackerRepository;
import com.foodys.app.repository.DriverRepository;
import com.foodys.app.service.TicketService;
import com.foodys.app.service.UserService;



@RestController
public class TicketRestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TicketService	ticketService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DriverRepository driverRepo;
//	
//	@Autowired
//	private TrackerRepository trackerRepo;

	@GetMapping("/ticket")
	public List<Ticket> gettickets(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =  userService.findUserByEmail(auth.getName());
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	 if(roles.get(0).equalsIgnoreCase("DRIVER")) {
    		return ticketService.findTicketByDriver_id(user.getId());	
    	}else if(roles.get(0).equalsIgnoreCase("ADMIN")){
    		return ticketService.findAll();
    	}
    	
		return null;
	}
	
	@Transactional
	@PostMapping("/ticket")
	public ResponseEntity<?> saveticket(@Valid @RequestBody Ticket ticket, BindingResult bindingResult) {
		String fieldName="";
		String errorMsg="";
		
		JSONObject responce = new JSONObject();
		JSONObject errors = new JSONObject();
		

		try {
		if (bindingResult.hasErrors()) {
			
			 logger.info("ticket->{} Binding results {}",ticket,bindingResult.getAllErrors());
			
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
			Driver driver =  driverRepo.findDriverByEmail(auth.getName());
			ticket.setDriver(driver);	
			ticket.setVerified("Submitted");
			ticket.setId(ticketService.save(ticket));
			
//			Tracker tracker = new Tracker(ticket,Ver.SUBMITTED,"ticket has been registered");
//			trackerRepo.save(tracker);
			
			responce.put("type", "success");
			responce.put("id", ticket.getId());
	
		}
		
		 return ResponseEntity.ok(responce.toString());
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
		 
		 return ResponseEntity.ok("Failed parsing JSON objects");
	}
	
	@PutMapping("/ticket")
	public ResponseEntity<?> updateticket(@Valid @RequestBody Ticket ticket, BindingResult bindingResult){
		
		String fieldName="";
		String errorMsg="";
		
		JSONObject responce = new JSONObject();
		JSONObject errors = new JSONObject();
		

		try {
			
		if (bindingResult.hasErrors()) {
			
			 logger.info("ticket->{} Binding results {}",ticket,bindingResult.getAllErrors());
			
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
			
		ticket = ticketService.update(ticket);
		
			responce.put("type", "success");
		
	
		}
		
		 return ResponseEntity.ok(responce.toString());
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(responce);
		
	}
	
	// delete can be added here
	
	@DeleteMapping("/ticket/{id}")
	public ResponseEntity<?> deleteticket(@PathVariable long id){
		
		
		ticketService.delete(id);
		return ResponseEntity.ok(id);
		
	}
	
	
	@Transactional
	@PostMapping("/admin/ticket/approve/{id}")
	public ResponseEntity<?> approveTicket(@PathVariable long id){
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user =  userService.findUserByEmail(auth.getName());
    	
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	System.out.println("Role from ticket rest approve:"+roles.get(0));
		if(roles.get(0).equalsIgnoreCase("ADMIN")) {
		
		
		Ticket ticket = ticketService.findTicketById(id);
		ticket.setVerified("Approved");
		ticketService.save(ticket);
		
		return ResponseEntity.ok(id);
		
		}else {
			return (ResponseEntity<?>) ResponseEntity.status(404);
		}
		
	}
	
	@Transactional
	@PostMapping("/admin/ticket/reject/{id}")
	public ResponseEntity<?> rejectTicket(@PathVariable long id){
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user =  userService.findUserByEmail(auth.getName());
    	
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	
		if(roles.get(0).equalsIgnoreCase("ADMIN")) {
		
		
		Ticket ticket = ticketService.findTicketById(id);
		ticket.setVerified("Rejected");
		ticketService.save(ticket);
		return ResponseEntity.ok(id);
		
		}else {
			return (ResponseEntity<?>) ResponseEntity.status(404);
		}
		
	}
	
	
	
	
}
