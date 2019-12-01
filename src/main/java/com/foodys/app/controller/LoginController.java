package com.foodys.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.foodys.app.models.AuthenticationRequest;
import com.foodys.app.models.AuthenticationResponse;
import com.foodys.app.models.ROLES;
import com.foodys.app.models.Role;
import com.foodys.app.models.Provider;
import com.foodys.app.models.Driver;
import com.foodys.app.models.User;
import com.foodys.app.repository.RoleRepository;
import com.foodys.app.repository.ProviderRepository;
import com.foodys.app.repository.DriverRepository;
import com.foodys.app.service.MyUserDetailsService;
import com.foodys.app.service.UserService;
import com.foodys.app.util.JwtUtil;





@Controller
public  class LoginController {
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	
	
	@Autowired
    private RoleRepository roleRepository;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProviderRepository providerRepo;

	@Autowired
	private DriverRepository driverRepo;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(HttpSession session){
		
		ModelAndView modelAndView = new ModelAndView();
		
			modelAndView.addObject("userName", "" );
			modelAndView.addObject("successMessage", "");
			modelAndView.setViewName("login");
			return modelAndView;


	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws Exception {
		

		logger.info("Username is:"+authenticationRequest.getUsername());
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
		
		
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		
		
		ModelAndView modelAndView = new ModelAndView();

		
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
		
		
	}
	
	@RequestMapping(value="provider/registration", method = RequestMethod.GET)
	public ModelAndView providerRegistration(){
		
		
		ModelAndView modelAndView = new ModelAndView();
		Provider provider = new Provider();
		modelAndView.addObject("provider", provider);
		modelAndView.setViewName("provider_registration");
		return modelAndView;
		
		
	}
	
	@RequestMapping(value="driver/registration", method = RequestMethod.GET)
	public ModelAndView driverRegistration(){
		
		
		ModelAndView modelAndView = new ModelAndView();
		Driver driver = new Driver();
		modelAndView.addObject("driver", driver);
		modelAndView.setViewName("driver_registration");
		return modelAndView;
		
		
	}
	
	@Transactional
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		User userExits = userService.findUserByEmail(user.getEmail());
		
		if (userExits != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the username provided");

		
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			
			user.setVerified(true); 
			user.setActive(1);
			Random rnd = new Random();
			int otp = 100000 + rnd.nextInt(900000);
			user.setOtp(otp);
			
			Role provider_role = new Role(2,"ADMIN");
		   
			
			
			user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
			long id = userService.save(user);
			user.setId(id);
			user.setRoles(new HashSet<Role>(Arrays.asList(provider_role)));
			userService.save(user);
			
			
//			Mail mail = new Mail();
//			mail.sendMail(author);
			
			modelAndView.addObject("successMessage", "Registration Successful, Login to continue.");
			
//			modelAndView.addObject("user", new User());
//			autoLogin(user,request );
			
			modelAndView.setViewName("login");
			
		}
		return modelAndView;
	}
	
	@Transactional
	@RequestMapping(value = "/provider/registration", method = RequestMethod.POST)
	public ModelAndView createNewProvider(@Valid Provider provider, BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		User providerExists = providerRepo.findProviderByEmail(provider.getEmail());
		
		if (providerExists != null) {
			
			logger.info("Inside if provider already exists");
			bindingResult
					.rejectValue("email", "error.provider",
							"There is already a Provider registered with the username provided");			
			
			logger.info("Bindresult:"+bindingResult.getAllErrors());
			modelAndView.setViewName("provider_registration");
			logger.info("Adding provider to the model");
			
			
		}
		if (bindingResult.hasErrors()) {
			
			logger.info("Inside some other exists");
			modelAndView.setViewName("provider_registration");
			
			logger.info("Adding provider to the model from some other exists");
			
			
			logger.info("Bindresult:"+bindingResult.getAllErrors());
		} else {
			
			provider.setVerified(true); 
			provider.setActive(1);
			Random rnd = new Random();
			int otp = 100000 + rnd.nextInt(900000);
			provider.setOtp(otp);
			
			Role provider_role = new Role(2,"PROVIDER");
		   
			
			
			provider.setPassword( new BCryptPasswordEncoder().encode(provider.getPassword()));
			provider = providerRepo.save(provider);
			provider.setRoles(new HashSet<Role>(Arrays.asList(provider_role)));
			providerRepo.save(provider);
			

			modelAndView.setViewName("provider_registration");
			modelAndView.addObject("successMessage", "Registration Successful, Login to continue.");
					

		}
		return modelAndView;
	}
	
	@Transactional
	@RequestMapping(value = "/driver/registration", method = RequestMethod.POST)
	public ModelAndView createNewDriver(@Valid Driver driver, BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		User driverExists = driverRepo.findDriverByEmail(driver.getEmail());
		
		if (driverExists != null) {
			bindingResult
					.rejectValue("email", "error.driver",
							"There is already a Driver registered with the username provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("driver_registration");
		} else {
			
			driver.setVerified(true); 
			driver.setActive(1);
			Random rnd = new Random();
			int otp = 100000 + rnd.nextInt(900000);
			driver.setOtp(otp);
			
			Role provider_role = new Role(2,"DRIVER");
		   
			
			
			driver.setPassword( new BCryptPasswordEncoder().encode(driver.getPassword()));
			driver = driverRepo.save(driver);
			driver.setRoles(new HashSet<Role>(Arrays.asList(provider_role)));
			driverRepo.save(driver);
			
			
			modelAndView.setViewName("driver_registration");
			modelAndView.addObject("successMessage", "Registration Successful, Login to continue.");
			

		}
		return modelAndView;
	}
	
	@RequestMapping({ "/home" })
	public ModelAndView firstPage() {
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user =  userService.findUserByEmail(auth.getName());
    	
		List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	
		if(roles.get(0).equalsIgnoreCase("PROVIDER")) {
		modelAndView.setViewName("provider_foodOrder");
		}else if(roles.get(0).equalsIgnoreCase("DRIVER")) {
			modelAndView.setViewName("driver_ticket");
		}else if(roles.get(0).equalsIgnoreCase("ADMIN")) {
			modelAndView.setViewName("admin_foodOrders");
		}
		
		return modelAndView;
	}

	
	public void logout() {
	    HttpServletRequest request =
	        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
	    new SecurityContextLogoutHandler().logout(request, null, null);
	}

	
}