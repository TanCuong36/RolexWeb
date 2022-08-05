package com.fpt.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Mail")
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "Email",columnDefinition="varchar(max)")
	String email;
	
	@Column(name = "Fullname",columnDefinition="nvarchar(50)")
	String fullname;
	
	@Column(name = "Ngaygui",columnDefinition="datetime")
	Date ngaygui = new Date();
	
	@Column(name = "Noidung",columnDefinition="nvarchar(max)")
	String noidung;
}
