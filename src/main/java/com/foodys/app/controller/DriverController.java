package com.foodys.app.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@Controller
public  class DriverController {
	
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@RequestMapping(value={"driver/foodOrder"}, method = RequestMethod.GET)
	public ModelAndView login(HttpSession session){
		
		ModelAndView modelAndView = new ModelAndView();
		
		
			modelAndView.setViewName("driver_foodOrder");
			return modelAndView;


	}
	
	@RequestMapping(value={"driver/transaction"}, method = RequestMethod.GET)
	public ModelAndView transaction(HttpSession session){
		
		ModelAndView modelAndView = new ModelAndView();
		
		
			modelAndView.setViewName("driver_transaction");
			return modelAndView;


	}
	
	
}