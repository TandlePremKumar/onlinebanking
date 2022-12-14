package com.bank.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.dao.UserRepository;
import com.bank.entities.User;
import com.bank.helper.Message;





@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model)
	{
		
		
		model.addAttribute("title","home -online banking");
		return "home";
	}
	@RequestMapping("/about")
	public String about(Model model)
	{
		
		
		model.addAttribute("title","about -online banking");
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		
		
		model.addAttribute("title","register -online banking");
		model.addAttribute("user",new User());
		
		return "signup";
	}
    @RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,HttpSession session)
	{
    	try {
    		
    		
    		if(!agreement)
        	{
        		System.out.println("you have not agreed the terms and conditions");
        		throw new Exception("you have not agreed the terms and conditions");
        	}
        	
        	user.setRole("ROLE_USER");
        	user.setEnabled(true);
        	user.getImageUrl();
        	user.setPassword(passwordEncoder.encode(user.getPassword()));
        	
        	
        	System.out.println("Agreement"+agreement);
        	System.out.println("USER"+user);
        	
        	User result=this.userRepository.save(user);
        	
        	
        	model.addAttribute("user",new User());
        	session.setAttribute("message",new Message("successfully Registered"," alert-Success" ));
    		return "signup";
    	}catch(Exception e) {
    		
    		e.printStackTrace();
    		model.addAttribute("user",user);
    		session.setAttribute("message",new Message("something went wrong"+e.getMessage(),"alert-danger" ));
    		return "signup";
    	}
		
	}
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		
		model.addAttribute("title","Login page");
		return "login";
	}
	
}
