package com.fpt.Support;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;
	
	public String getString(String name, String defaultValue){
		
		return name;
	}
	
	public int getInt(String name, int defaultValue){
		
		return 1;
	}
	
	public double getDouble(String name, double defaultValue){
		
		return 1;
	}
	
	public boolean getBoolean(String name, boolean defaultValue){
		
		return true;
	}
	
	public Date getDate(String name, String pattern){
		
		return new Date();
	}
	
	public File save(MultipartFile file, String path) {
		
		return new File("");
	}
}	

