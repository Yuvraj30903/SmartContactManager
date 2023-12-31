package com.smart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.config.EmailService;
import com.smart.entities.LoginData;
import com.smart.entities.User;
import com.smart.repo.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller 
public class TestController {
 
	 
	Logger log=LoggerFactory.getLogger(TestController.class);
	@Autowired
	private EmailService emailService; 
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	@Autowired
	private UserDetailsService userDetailsService; 
	@Autowired
	private UserRepo userRepo;

	@GetMapping("/")
	public String home(Model model) {
		 
		log.info("Home:execution started");
		model.addAttribute("title", "Home-Smart Contact Manager");
		log.info("Home:execution completed");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model, HttpSession session) {
		model.addAttribute("title", "Sign Up-Smart Contact Manager");
		User user = new User();
		model.addAttribute("user", user);
		session.setAttribute("msg", null);
		return "signup";
	}

	@GetMapping("/signin")
	public String login(Model model, HttpSession session) {
		model.addAttribute("title", "Login-Smart Contact Manager");
		model.addAttribute("loginData", new LoginData());
//		if((boolean)session.getAttribute("type")) 
		return "login";
	}

	@PostMapping("/signup")
	public String do_register(@Valid  @ModelAttribute("user") User user,BindingResult result, Model model, RedirectAttributes r) {
		model.addAttribute("title", "Sign Up-Smart Contact Manager"); 
		if(result.hasErrors())
		{ 
			return "signup";
		}  
		r.addFlashAttribute("type", "alert-success");
		r.addFlashAttribute("msg", "User Registred Successfully...");
		user.setEnabled(true);
		user.setRole("ROLE_USER");
		user.setImageUrl("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
//		myConfig.addUser(user.getEmail(), user.getPassword(), "USER");
		String body = "Use your email as userName... \n" + "UserName: \t" + user.getEmail() + "\n"
				+ "Thank you for registering in this app" + "\n"
				+ "THIS IS AUTOGENERATED MAIL DO NOT REPLAY TO THIS MAIL";
		
		
		// Sendinng email to Registered user
//		 emailService.sendEmail("Registered to Smart Contact Manager[Auto Generated]",body, user.getEmail());
		return "redirect:/signup";

	}
	
	
	
	@GetMapping("/forgot")
	public String forgot(Model m)
	{
		m.addAttribute("title","Forgot Password");
		
		return "forgot";
	}
	
	@PostMapping("/do_forgot")
	public String doforgot(@RequestParam("email") String email,Model m,RedirectAttributes r,HttpSession session)
	{

		m.addAttribute("title","Enter OTP");

//		System.out.println(email);
		User user = userRepo.getUserByUserName(email);
//		System.out.println(user);
		session.setAttribute("user", user);
		if(user==null)
		{
			r.addFlashAttribute("msg", "Your email is not registered!!!!!");
			return "redirect:/forgot";
		}
		int otp=(int) ((Math.random() * (999999 - 100000)) + 100000); 
	
		session.setAttribute("otp",otp);
		String body="OTP:"+otp+"\nThis is one time password generated by Smart contact manager for changing password\n.Do not share this password with anyone";
		// Sendinng email to Registered user;
		emailService.sendEmail("OTP from Smart Contact Manager[Auto Generated]",body,"21bce226@nirmauni.ac.in");
		return "getotp";
	}

	
	@PostMapping("/chngpass")
	public String changePass(Model m,@RequestParam("givenotp") Integer givenotp,HttpSession session,RedirectAttributes r)
	{
		Integer otp=(Integer)session.getAttribute("otp");  
		if(otp.equals(givenotp)) {
			m.addAttribute("title","Change password");
		return "chngpass";
		}
		else {
			
			m.addAttribute("title","Enter OTP");
			m.addAttribute("msg", "OTP does not match!!!");
			return "getotp";
			
		}
	}
	
	
	@PostMapping("/process_chng")
	public String processpass(@RequestParam("newpass") String newpass,
			@RequestParam("confirmpass") String confirmpass,RedirectAttributes r,Model m,HttpSession session)
	{
		if(!confirmpass.equals(newpass))
		{
			m.addAttribute("msg","New password and cofirm password must be same!!");
			return "chngpass";
			
			
		}
		User user = (User) session.getAttribute("user");
		user.setPassword(passwordEncoder.encode(newpass));
		userRepo.save(user);
		r.addFlashAttribute("msg","Password has been successfully changed");
		return "redirect:/signin";
//		r.addAttribute("msg","Password has been successfully changed");
//		return "redirect:signin";
	}
 	
	

}
