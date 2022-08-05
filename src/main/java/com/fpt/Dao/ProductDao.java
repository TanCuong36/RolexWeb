package com.fpt.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import com.fpt.Model.Product;

@Service
public interface ProductDao extends JpaRepository<Product, String>{
	
	@Query("FROM Product p Where p.id = ?1")
	Product findbyid(String id);
	
	@Query("FROM Product p Where p.name like ?1")
	Page findkeyword(String keyword,Pageable page);
	
	@Query(value="select top(3) * from products order by soluongban desc",nativeQuery=true) 
	List<Product> top3();
	 
	@Query(value="select top(6) * from products order by date_add desc",nativeQuery=true)
	List<Product> top6();
	
		
}
