package com.springboot.web;

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 

@Controller
public class MainController {
	 
	
	
	
	@RequestMapping("/")
	public String indexHandler()
	{
		return "redirect:home";
	}
	@RequestMapping("/home")
	public String homehandler()
	{ 
		return "home";
	}
	@RequestMapping("/contact")
	public String conatacthandler()
	{ 
		return "contact";
	}

}
