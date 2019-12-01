package com.foodys.app.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@Controller
public  class ProviderController {
	
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	
	
	@RequestMapping(value={"/provider/transaction"}, method = RequestMethod.GET)
	public ModelAndView transaction(HttpSession session){
		
		ModelAndView modelAndView = new ModelAndView();
		
		
			modelAndView.setViewName("provider_transaction");
			return modelAndView;


	}
	
	
}