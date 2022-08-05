package com.fpt.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpt.Dao.AccountDao;
import com.fpt.Dao.CartDao;
import com.fpt.Dao.OrderDao;
import com.fpt.Dao.ProductDao;
import com.fpt.Model.Account;
import com.fpt.Model.Cart;
import com.fpt.Model.Mail;
import com.fpt.Model.OrderDetail;
import com.fpt.Model.Product;
import com.fpt.Model.ReportOrder;
import com.fpt.Support.CookieService;

@Controller
@RequestMapping("/")
public class index {
	boolean check = false;

	@Autowired
	CookieService cookieService;
	@Autowired
	OrderDao orderdao;
	@Autowired
	AccountDao dao;
	@Autowired
	ProductDao productdao;
	@Autowired
	CartDao cartdao;
	@Autowired
	HttpServletResponse reps;

	@GetMapping("TrangChu")
	public String index(Model model) {
		try {
			Cookie cookie = cookieService.get("user");
			double total = 0;
			for (Cart c : cartdao.findById(cookie.getValue())) {
				total += c.getProduct().getPrice() * c.getQuantity();
			}
			model.addAttribute("total", total);
			model.addAttribute("totalitem", cartdao.findById(cookie.getValue()).size());
			model.addAttribute("cartitem", cartdao.findById(cookie.getValue()));
			model.addAttribute("account", dao.findByUsername(cookie.getValue()));
			model.addAttribute("navlogin", true);
		} catch (Exception e) {
			model.addAttribute("checkdn", true);
		}
		cookieService.add("sort", "name", 2);
		cookieService.add("search", "", 2);
		model.addAttribute("nologin", model.getAttribute("nologin"));
		model.addAttribute("producttop3", productdao.top3());
		model.addAttribute("producttop6", productdao.top6());
		model.addAttribute("email",new Mail());
		return "index";
	}

	void caritem(Model model) {
		Cookie cookie = cookieService.get("user");
		double total = 0;
		for (Cart c : cartdao.findById(cookie.getValue())) {
			total += c.getProduct().getPrice() * c.getQuantity();
		}
		model.addAttribute("total", total);
		model.addAttribute("cartitem", cartdao.findById(cookie.getValue()));
	}

	@GetMapping("TrangChu/Cart")
	public String maycart(Model model) {
		Cookie cookie = cookieService.get("user");
		caritem(model);
		model.addAttribute("account", dao.findByUsername(cookie.getValue()));
		model.addAttribute("navlogin", true);
		return "Cart";
	}

	@GetMapping("TrangChu/Product/Addcart")
	public String productpages(Model model, @RequestParam("id") String idproduct) {
		try {
			Cart cart = cartdao.findByUsAndId(cookieService.getValue("user"), idproduct);
			if (cart == null) {
				Cart item = new Cart();
				item.setQuantity(1);
				item.setProduct(productdao.findbyid(idproduct));
				item.setAccount(dao.findByUsername(cookieService.getValue("user")));
				cartdao.save(item);
				/* System.out.println("Add Thành Công"); */
			} else {
				cart.setQuantity(1 + cart.getQuantity());
				cartdao.save(cart);
			}
		} catch (Exception e) {
			check = true;
		}
		return "redirect:/TrangChu/Product";
	}

	@GetMapping("TrangChu/Addcart")
	public String addcart(Model model, @RequestParam("id") String idproduct) {
		try {
			Cart cart = cartdao.findByUsAndId(cookieService.getValue("user"), idproduct);
			if (cart == null) {
				Cart item = new Cart();
				item.setQuantity(1);
				item.setProduct(productdao.findbyid(idproduct));
				item.setAccount(dao.findByUsername(cookieService.getValue("user")));
				cartdao.save(item);
				/* System.out.println("Add Thành Công"); */
			} else {
				cart.setQuantity(1 + cart.getQuantity());
				cartdao.save(cart);
			}
		} catch (Exception e) {

		}
		return "redirect:/TrangChu";
	}

	@GetMapping("TrangChu/{uri}/Removecart")
	public String removecart(Model model, @PathVariable("uri") String uri, @RequestParam("id") String idproduct) {
		System.out.println(uri);
		Cookie cookie = cookieService.get("user");
		if (uri.equals("Cart")) {
			Cart cart = cartdao.findByUsAndId(cookie.getValue(), idproduct);
			cartdao.delete(cart);
			caritem(model);
			return "redirect:/TrangChu/Cart";
		}
		if (uri.equals("CartAll")) {
			for (Cart x : cartdao.findById(cookie.getValue())) {
				cartdao.delete(x);
			}
			return "redirect:/TrangChu/Cart";
		}
		Cart cart = cartdao.findByUsAndId(cookie.getValue(), idproduct);
		cartdao.delete(cart);
		return "redirect:/TrangChu";
	}

	@GetMapping("cart")
	public String editcart(@RequestParam("nut") String status, @RequestParam("id") String id) {
		Cookie cookie = cookieService.get("user");
		Cart x = cartdao.findByUsAndId(cookie.getValue(), id);
		if (status.equalsIgnoreCase("cartminus")) {
			if (x.getQuantity() <= 1) {
				cartdao.delete(x);
			} else {
				x.setQuantity(x.getQuantity() - 1);
				cartdao.save(x);
			}
		} else {
			x.setQuantity(x.getQuantity() + 1);
			cartdao.save(x);
		}
		return "redirect:/TrangChu";
	}

	@GetMapping("Mycart")
	public String Mycart(Model model, @RequestParam("nut") String status, @RequestParam("id") String id) {
		Cookie cookie = cookieService.get("user");
		Cart x = cartdao.findByUsAndId(cookie.getValue(), id);
		if (status.equalsIgnoreCase("cartminus")) {
			if (x.getQuantity() <= 1) {
				cartdao.delete(x);
			} else {
				x.setQuantity(x.getQuantity() - 1);
				cartdao.save(x);
			}
		} else {
			x.setQuantity(x.getQuantity() + 1);
			cartdao.save(x);
		}
		return "redirect:/TrangChu/Cart";
	}

	@GetMapping("TrangChu/Order")
	public String order(Model model) {
		Cookie cookie = cookieService.get("user");
		caritem(model);
		model.addAttribute("account", dao.findByUsername(cookie.getValue()));
		return "CartDetail";
	}

	@GetMapping("TrangChu/Order/Pay")
	public String payorder() {
		Cookie cookie = cookieService.get("user");
		int rand = (int) (Math.random() * 89999) + 10000;
		for (Cart cart : cartdao.findById(cookie.getValue())) {
			OrderDetail order = new OrderDetail();
			order.setMadh("HD" + rand);
			order.setProduct(cart.getProduct());
			order.setAccount(cart.getAccount());
			order.setPrice(cart.getProduct().getPrice());
			order.setQuantity(cart.getQuantity());
			order.setStatus("PENDING");
			order.setToalprice(cart.getProduct().getPrice() * cart.getQuantity());
			orderdao.save(order);
		}
		return "redirect:/TrangChu";
	}

	@GetMapping("TrangChu/Product")
	public String product(Model model, @RequestParam("sort") Optional<Object> sort,
			@RequestParam("page") Optional<Integer> trang, @RequestParam("search") Optional<String> search) {
		String sorttype = "" + sort.orElse(cookieService.getValue("sort"));
		cookieService.add("sort", sorttype, 2);
		if (search == null) {
			Sort sortpage = Sort.by("" + sorttype).descending();
			Pageable x = PageRequest.of(trang.orElse(0), 6, sortpage);
			Page<Product> page = productdao.findkeyword("%%", x);
			model.addAttribute("pages", page);
		} else {
			String seachtype = search.orElse(cookieService.getValue("search"));
			cookieService.add("search", seachtype, 2);
			Sort sortpage = Sort.by("" + sorttype).descending();
			Pageable x = PageRequest.of(trang.orElse(0), 6, sortpage);
			Page<Product> page = productdao.findkeyword("%" + seachtype + "%", x);
			model.addAttribute("pages", page);
		}
		model.addAttribute("check", check);
		check = false;
		return "Product";
	}

	/* =================== Detail =================== */

	List<ReportOrder> rppending(Model model, String status) {
		List<OrderDetail> orderDetails = orderdao.findoder(status, cookieService.getValue("user"));
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
		model.addAttribute("list", list);
		return list;
	}

	@GetMapping("TrangChu/User/Detail")
	public String detail(Model model, @RequestParam("status") Optional<String> status) {
		switch (status.orElse("pending")) {
		case "packing": {
			rppending(model, "PACKING");
			model.addAttribute("packing", true);
			break;
		}
		case "success": {
			rppending(model, "SUCCESS");
			model.addAttribute("success", true);
			break;
		}
		case "cancel": {
			rppending(model, "CANCEL");
			model.addAttribute("cancel", true);
			break;
		}
		default:
			rppending(model, "PENDING");
			model.addAttribute("pending", true);
			break;
		}
		return "Detail";
	}

	@GetMapping("TrangChu/User/Detail/St")
	public String status(@RequestParam("st") Optional<String> st,@RequestParam("hd")String dh) {
		switch (st.orElse("")) {
		case "huy": {
			System.out.println(dh);
			for(OrderDetail detail: orderdao.findMadhAndUser(dh,cookieService.getValue("user"))){
				detail.setStatus("CANCEL");
				detail.setDateStatus(new Date());
				orderdao.save(detail);
			}			
			break;
		}
		case "xoa": {
			System.out.println(dh);
			break;
		}
		case "mualai": {
			System.out.println(dh);
			for(OrderDetail detail: orderdao.findMadhAndUser(dh,cookieService.getValue("user"))){
				detail.setStatus("PENDING");
				detail.setNgaymua(new Date());
				orderdao.save(detail);
			}	
			break;
		}
		default:
		 
		}
		return "redirect:/TrangChu/User/Detail";
	}
		

}
