package com.fpt.controller;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpt.Dao.AccountDao;
import com.fpt.Dao.MailerServiceImpl;
import com.fpt.Dao.OrderDao;
import com.fpt.Dao.ProductDao;
import com.fpt.Model.OrderDetail;
import com.fpt.Model.Product;
import com.fpt.Model.ReportOrder;

@Controller
@RequestMapping("/")
public class AdminController {
	List<Integer> totalstatus = new ArrayList<>();
	
	@Autowired
	AccountDao account;
	@Autowired
	ProductDao productdao;
	@Autowired
	OrderDao orderdao;
	@Autowired
	MailerServiceImpl mail;

	List<ReportOrder> rppending(Model model, String status) {
		List<OrderDetail> orderDetails = orderdao.findByStatus(status);
		List<ReportOrder> list = new ArrayList<>();
		String check = "";	
		for (OrderDetail detail : orderDetails) {
			if (!check.equals(detail.getMadh())) {
				ReportOrder ts = new ReportOrder();
				List<OrderDetail> order = new ArrayList<>();
				float total = 0;
				ts.setMa(detail.getMadh());
				check = detail.getMadh();
				for (OrderDetail detail2 : orderDetails) {
					if (check.equals(detail2.getMadh())) {
						order.add(detail2);
					}
				}
				ts.setDetails(order);
				ts.setAccount(detail.getAccount());
				for (OrderDetail x : order)
					total += x.getToalprice();
				ts.setTotalOrder(total);
				list.add(ts);
			}
		}
		model.addAttribute("list",list);
		return list;
	}

	void totallist(Model model) {
		totalstatus.clear();
		totalstatus.add(rppending(model, "PACKING").size());
		totalstatus.add(rppending(model, "SUCCESS").size());
		totalstatus.add(rppending(model, "CANCEL").size());
		totalstatus.add(rppending(model, "PENDING").size());
	}

	@GetMapping("Admin")
	public String admin(Model model) {
		totallist(model);
		model.addAttribute("status", "pending");
		model.addAttribute("totalstatus", totalstatus);
		return "Admin/index";
	}

	@GetMapping("Admin/Pending")
	public String pending() {
		String check = "";
		List<OrderDetail> OrderDetail = orderdao.findByStatus("PENDING");
		for (OrderDetail order : OrderDetail) {
			order.setStatus("PACKING");
			order.setDateStatus(new Date());
			orderdao.save(order);
			if (!check.equals(order.getMadh())) {
				mail.sendEmail("longzu102@gmail.com", "Thông Tin Đơn Hàng", "Đơn Hàng có mã: " + order.getMadh()
						+ " Đã được xác nhận. Để xem chi tiết vào lòng vào xem đơn hàng để biết thêm thông tin.");
				check = order.getMadh();
			}
		}
		return "redirect:/Admin";
	}

	@GetMapping("Admin/Forms")
	public String form(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("disbtn", false);
		return "Admin/forms";
	}

	@GetMapping("Admin/Forms/Edit")
	public String edit(Model model, @RequestParam("id") String id) {
		model.addAttribute("product", productdao.findbyid(id));
		model.addAttribute("disbtn", false);
		return "Admin/forms";
	}

	@GetMapping("Admin/Forms/List")
	public String listproduct(Model model,@RequestParam("page")Optional<Integer> page) {
		Sort sortpage = Sort.by("dateAdd").descending();
		Pageable setpages = PageRequest.of(page.orElse(0),5,sortpage);
		Page<Product> list = productdao.findAll(setpages);
		model.addAttribute("listsp",list);
		return "Admin/listproduct";
	}

	@GetMapping("Admin/Forms/List/delete")
	public String deproduct(Model model, @RequestParam("iddelete") String id) {
		productdao.deleteById(id);
		return "redirect:/Admin/Forms/List";
	}

	@PostMapping("Admin/Forms")
	public String addform(Model model, @RequestParam("button") String button, @ModelAttribute("product") Product data) {
		switch (button) {
		case "INSERT": {
			if (productdao.findbyid(data.getId()) == null) {
				productdao.save(data);
				model.addAttribute("success", true);
				model.addAttribute("message", "THÊM SẢN PHẨM THÀNH CÔNG!!!");
			} else {
				model.addAttribute("message", "Mã Sản Phẩm Đã Tồn Tại!!!");
				model.addAttribute("error", true);
			}
			break;
		}
		case "UPDATE": {
			if (productdao.findbyid(data.getId()) != null) {
				productdao.save(data);
				model.addAttribute("success", true);
				model.addAttribute("message", "CẬP NHẬT SẢN PHẨM THÀNH CÔNG!!!");
			} else {
				model.addAttribute("message", "Mã Sản Phẩm Không Tồn Tại!!!");
				model.addAttribute("updateError", true);
			}
			break;
		}
		case "DELETE": {
			if (productdao.findbyid(data.getId()) != null) {
				productdao.delete(data);
				model.addAttribute("success", true);
				model.addAttribute("message", "XOÁ SẢN PHẨM THÀNH CÔNG!!!");
			} else {
				model.addAttribute("message", "Mã Sản Phẩm Không Tồn Tại!!!");
				model.addAttribute("deleteError", true);
			}
			break;
		}
		default:
			form(model);
		}
		return "Admin/forms";
	}

	@GetMapping("Admin/Report")
	public String report(Model model, @RequestParam("report") String rp) {
		switch (rp) {
		case "pending": {
			totallist(model);
			rppending(model, "PENDING");
			model.addAttribute("status", "pending");
			break;
		}
		case "packing": {
			totallist(model);
			rppending(model, "PACKING");
			model.addAttribute("status", "packing");
			break;
		}
		case "success": {
			totallist(model);
			rppending(model, "SUCCESS");
			model.addAttribute("status", "success");
			break;
		}
		case "cancel": {
			totallist(model);
			rppending(model, "CANCEL");
			model.addAttribute("status", "cancel");
			break;
		}
		}
		model.addAttribute("totalstatus", totalstatus);
		return "Admin/index";
	}

	@PostMapping("Admin/Packing")
	public String packing(@RequestParam("ma") String[] ma) {
		for (int i = 0; i < ma.length; ++i) {
			List<OrderDetail> orders = orderdao.findByMadh(ma[i]);
			for (OrderDetail order : orders) {
				order.setStatus("SUCCESS");
				order.getProduct().setSoluongban(order.getProduct().getSoluongban() + order.getQuantity());
				order.setDateStatus(new Date());
				orderdao.save(order);
			}
		}
		return "redirect:/Admin/Report?report=packing";
	}
	
	@GetMapping("Admin/User")
	public String user(Model model){
		model.addAttribute("account",account.findByAdmin(false));
		return "Admin/user";
	}
	@GetMapping("Admin/User/Remove")
	public String user(@RequestParam("id")String id){
		account.delete(account.findByUsername(id));
		return "redirect:/Admin/User";
	}
	

}
