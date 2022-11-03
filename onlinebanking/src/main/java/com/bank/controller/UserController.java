package com.bank.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bank.dao.CardsRepository;
import com.bank.dao.LoanRepository;
import com.bank.dao.UserRepository;
import com.bank.entities.Cards;
import com.bank.entities.Loan;
import com.bank.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private CardsRepository cardsRepository;
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		

		String userName= principal.getName();
		System.out.println("USER NAME"+userName);
		
		User user=userRepository.getUserByUserName(userName);
		System.out.println("USER "+user);
		model.addAttribute("user",user);
		
		
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{
		
		
		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	@GetMapping("/add-loan")
	public String openAddLoanForm(Model model)
	{
		
		model.addAttribute("title","Add Loan");
		model.addAttribute("loan",new Loan());
		
		return "normal/add_loan_form";
	}
	@PostMapping("/process-loan")
	public String processLoan(@ModelAttribute Loan loan,Principal principal)
	{
		
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
		loan.setUser(user);
		user.getLoans().add(loan);
		this.userRepository.save(user);
		System.out.println("Added Sucessfully");
		
		System.out.println("data"+loan);
		return "normal/add_loan_form";
	}
	
	@GetMapping("/show-loans")
	public String showLoans(Model m,Principal principal)
	{
		
		m.addAttribute("title","show user loans");
		String UserName=principal.getName();
		User user= this.userRepository.getUserByUserName(UserName);
		List<Loan> loans=this.loanRepository.findLoansByUser(user.getId());
		m.addAttribute("loans",loans);
		
		return "normal/show_loans";
	}
	
	@GetMapping("/profile")
	public String yourProfile(Model model)
	{
		
		
		model.addAttribute("title","profile page");
		return "normal/profile";
	}
	
	
	@GetMapping("/add-cards")
	public String openCardsForm(Model model)
	{
		
		model.addAttribute("title","Add Cards");
		model.addAttribute("cards",new Cards());
		
		return "normal/add_cards_form";
	}
	
	
	
	@PostMapping("/process-cards")
	public String processCards(@ModelAttribute Cards cards,Principal principal)
	{
		
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
		cards.setUser(user);
		user.getCards().add(cards);
		this.userRepository.save(user);
		System.out.println("Added Sucessfully");
		
		System.out.println("data"+cards);
		return "normal/add_cards_form";
	}
	
	
	
	@GetMapping("/show-cards")
	public String showCards(Model m,Principal principal)
	{
		
		m.addAttribute("title","show user cards");
		String UserName=principal.getName();
		User user= this.userRepository.getUserByUserName(UserName);
		List<Cards> cards=this.cardsRepository.findCardsByUser(user.getId());
		m.addAttribute("cards",cards);
		
		return "normal/show_cards";
	}
	
	@PostMapping("/update-loan/{lid}")
	public String updateForm(@PathVariable("lid") Integer lid,Model m)
	{
		
		
		m.addAttribute("title","Update Loan");
		
		
	Loan loan=this.loanRepository.findById(lid).get();
	
	m.addAttribute("loan",loan);
		return "normal/update_form";
	}
	
	@RequestMapping(value="/process-update",method=RequestMethod.POST)
	public String updateHandler(@ModelAttribute Loan loan,Model m,Principal principal)
	
	{
		
		
		User user=this.userRepository.getUserByUserName(principal.getName());
		
		loan.setUser(user);
		this.loanRepository.save(loan);
		
		return "redirect:/user/"+loan.getLid()+"/loan";
	}
	
	
	
	
	
	
	
	
	

}
