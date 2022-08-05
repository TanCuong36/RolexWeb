package com.fpt.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.fpt.Model.Account;
import com.fpt.Model.Mail;

@Service
public interface EmailDao extends JpaRepository<Mail, Integer>{
	
	
}
