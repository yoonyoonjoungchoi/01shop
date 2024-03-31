package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String prodNo=(String)request.getParameter("prodNo");
		
		ProductVO productVO=new ProductVO();
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
	}
}

