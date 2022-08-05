package com.fpt.controller;


import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fpt.Dao.AccountDao;
import com.fpt.Model.Account;
import com.fpt.Support.CookieService;
import com.fpt.Support.SessionService;

@Controller
public class LoginController {
		
		@Autowired
		AccountDao dao;
		@Autowired
		HttpServletResponse reps;
		@Autowired		
		SessionService sessionService;
		@Autowired
		CookieService cookieService;
		@Autowired
		PasswordEncoder passwordEncoder;
	
		@GetMapping("Login")
		public String login(){
			return "Admin/pages/login";
		}
		
		@GetMapping("Logout")
		public String logout(Model model){
			cookieService.add("user","",0);
			sessionService.remove("admin");
			sessionService.remove("user");
			sessionService.remove("security-uri");
			return "redirect:/TrangChu";
			
		}
		
		@PostMapping("Login")
		public String checklogin(Model model,@RequestParam("username")String us,@RequestParam("password")String pw){
			Account user = dao.findByUsername(us);
			sessionService.set("user",user);
			if(user==null ||!BCrypt.checkpw(pw,user.getPassword())){
				model.addAttribute("error","Username Hoặc Mật Khẩu chưa Đúng");
				model.addAttribute("tb",true);
				return "Admin/pages/login";
			}else{
				if(sessionService.get("security-uri")==null) { sessionService.set("security-uri", "/Admin");}
				String uri = sessionService.get("security-uri");				
				if(user.isAdmin()){
					sessionService.set("admin",us);
					return "redirect:" + uri;
				}
				cookieService.add("user",us,2);
			}
			return "redirect:/TrangChu";
		}
		
		@GetMapping("Register")
		public String register(Model model){
			model.addAttribute("account",new Account());
			return "Admin/pages/create-account";
			
		}
		
		@PostMapping("Create")
		public String create(@ModelAttribute("account")Account data){
			Account account = data;
			account.setPassword(passwordEncoder.encode(account.getPassword()));
			System.out.println("Thêm Thành Công");
			dao.save(account);
			return "redirect:/Login";
		}
}
