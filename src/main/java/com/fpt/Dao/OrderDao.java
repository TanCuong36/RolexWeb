package com.fpt.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.fpt.Model.OrderDetail;

@Service
public interface OrderDao extends JpaRepository<OrderDetail, Long>{
	
	List<OrderDetail> findByStatus(String status);
	@Query("FROM OrderDetail o Where o.status=?1 AND o.account.username=?2")
	List<OrderDetail> findoder(String status,String username);
	List<OrderDetail> findByMadh(String madh);
	@Query("FROM OrderDetail o Where o.madh=?1 AND o.account.username=?2")
	List<OrderDetail> findMadhAndUser(String ma,String username);
	
	
}
