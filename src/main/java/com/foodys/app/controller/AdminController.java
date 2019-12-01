package com.foodys.app.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@Controller
public  class AdminController {
	
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@RequestMapping(value={"admin/ticket"}, method = RequestMethod.GET)
	public ModelAndView login(HttpSession session){
		
		ModelAndView modelAndView = new ModelAndView();
		
		
			modelAndView.setViewName("admin_tickets");
			return modelAndView;


	}
	
	@RequestMapping(value={"/admin/transaction"}, method = RequestMethod.GET)
	public ModelAndView adminTransaction(HttpSession session){
		
		ModelAndView modelAndView = new ModelAndView();
		
		
			modelAndView.setViewName("admin_transaction");
			return modelAndView;


	}
	
	
}