package com.fpt.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account implements Serializable {
	@Id
	@Column(name="Username",columnDefinition = "varchar(20)")
	String username;
	
	@Column(name="Password",columnDefinition = "varchar(100)")
	String password;
	
	@Column(name="Fullname",columnDefinition = "nvarchar(50)")
	String fullname;
	
	@Column(name="Email",columnDefinition = "varchar(max)")
	String email;
	
	@Column(name="Image",columnDefinition = "varchar(max)")
	String image;
	
	@Column(name="Admin",columnDefinition = "bit")
	boolean admin;
	
	@Column(name="GioiTinh",columnDefinition = "bit")
	boolean gioitinh;
	
	@Column(name="SDT",columnDefinition = "varchar(10)")
	String sdt;
	
	@Column(name="DiaChi",columnDefinition = "nvarchar(max)")
	String diachi;
	
	@OneToMany(mappedBy = "account") 
	List<Cart> cart;
	
	@OneToMany(mappedBy = "account") 
	List<OrderDetail> order;
	
	 
}