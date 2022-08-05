package com.fpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpt.Dao.EmailDao;
import com.fpt.Model.Mail;

@Controller
@RequestMapping("/")
public class MailController {
	@Autowired
	EmailDao email;
	
	@GetMapping("Admin/Mail")
	public String mail(Model model){
		return "Admin/mail";
	}
	
	@PostMapping("TrangChu/Email")
	public String email(@ModelAttribute("email")Mail mail) {
		email.save(mail);
		return "redirect:/TrangChu";
	}
}
