package com.fpt.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportOrder {
	String ma;
	List<OrderDetail> details;
	Account account;
	float totalOrder;
}
