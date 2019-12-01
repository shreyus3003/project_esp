package com.foodys.app.RestController;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodys.app.models.Role;
import com.foodys.app.models.Transaction;
import com.foodys.app.models.User;
import com.foodys.app.repository.TransactionRepository;
import com.foodys.app.service.UserService;



@RestController
public class TransactinoRestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository transactionRepo;

	
	
	@GetMapping("/transaction/fetch")
	public List<Transaction> getTransactions(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =  userService.findUserByEmail(auth.getName());
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	
    	System.out.println("*********foodOrder page role is:"+roles.get(0));
    	
    	if(roles.get(0).equalsIgnoreCase("PROVIDER")) {
    		return transactionRepo.findTransactionByDebitacc(user.getEmail());
    	}else if(roles.get(0).equalsIgnoreCase("DRIVER")) {
    		return transactionRepo.findTransactionByCreditacc(user.getEmail());	
    	}else if(roles.get(0).equalsIgnoreCase("ADMIN")){
    		
    		return transactionRepo.findTransactionByCreditacc("Foodys");	
    		
    	}
    	
		return null;
	}
	
	
	
	
	
	
}
