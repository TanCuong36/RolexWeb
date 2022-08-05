package com.fpt.Support;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;

	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie coke : cookies)
				if (coke.getName().equalsIgnoreCase(name))
					return coke;
		return null;

	}

	public String getValue(String name){		
		return get(name).getValue();
	}

	public void add(String name, String value, int hours) {
		Cookie cookie =new Cookie(name, value);
		cookie.setMaxAge(hours*60*60);
		cookie.setPath("/");
		response.addCookie(cookie);		
	}

	public void remove(String name) {
		
	}
}
