package com.fpt.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.fpt.Model.Account;

@Service
public interface AccountDao extends JpaRepository<Account, String>{
	
	Account findByUsernameAndPassword(String us,String pw);
	Account findByUsername(String username);
	@Query("FROM Account a Where a.admin=?1")
	List<Account> findByAdmin(Boolean x);
}
