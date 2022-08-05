package com.fpt.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import com.fpt.Model.Cart;

@Service
public interface CartDao extends JpaRepository<Cart, Long>{
	
	@Query("FROM Cart c Where c.account.username=?1")
	List<Cart> findById(String id);
	
	@Query("FROM Cart c Where c.account.username=?1 AND c.product.id=?2")
	Cart findByUsAndId(String us,String id);
	
}
