package com.fpt.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orderdetail")
public class OrderDetail implements Serializable{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "MaDH",columnDefinition="char(7)")
	String madh;
	
	@Column(name = "Price",columnDefinition="float")
	Double price;
	
	@Column(name = "Totalprice",columnDefinition="float")
	Double toalprice;
	
	/* @Temporal(TemporalType.DATETIME) */
	@Column(name = "NgayMua",columnDefinition="datetime")
	Date ngaymua = new Date();
	
	@Column(name = "DateStatus",columnDefinition="datetime")
	Date dateStatus;
	
	@Column(name = "quantity",columnDefinition="int")
	int quantity;
	
	@Column(name = "Status",columnDefinition="nvarchar(50)")
	String status;
	
	@ManyToOne
	@JoinColumn(name = "Productid")
	Product product;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Account account;

}
