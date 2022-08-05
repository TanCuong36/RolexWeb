package com.fpt.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
	@Id
	@Column(name = "ProductId", columnDefinition = "char(5)")
	String id;

	@Column(name = "Name", columnDefinition = "nvarchar(50)")
	String name;
	@Column(name = "Image", columnDefinition = "nvarchar(max)")
	String image;
	@Column(name = "Price", columnDefinition = "float")
	Double price;

	/* @Temporal(TemporalType.DATE) */
	@Column(name = "dateAdd", columnDefinition = "datetime")
	Date dateAdd = new Date();
	
	@Column(name = "Soluongban", columnDefinition = "int")
	int soluongban;
	
	@Column(name = "Mota", columnDefinition = "nvarchar(max)")
	String mota;

	@OneToMany(mappedBy = "product")
	List<Cart> cart;
	@OneToMany(mappedBy = "product")
	List<OrderDetail> order;
}
