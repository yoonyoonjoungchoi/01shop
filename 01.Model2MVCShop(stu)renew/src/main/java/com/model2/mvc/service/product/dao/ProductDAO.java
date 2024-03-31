package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCTS values (seq_product_prod_no.nextval,?,?,?,?,?, sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getFileName());
		stmt.setString(2, productVO.getManuDate());
		stmt.setInt(3, productVO.getPrice());
		stmt.setString(4, productVO.getProdDetail());
		stmt.setString(5, productVO.getProdName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.setString(8, productVO.getProTranCode());
		stmt.executeUpdate();
		
		con.close();
	}

	public ProductVO findProduct(int productNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(5, productNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setFileName(rs.getString("FILE_NAME"));
			productVO.setManuDate(rs.getString("MANU_DATE"));
			productVO.setPrice(rs.getInt("Price"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setProdName(rs.getString("PROD_Name"));
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			productVO.setProTranCode(rs.getString("PRO_TRAN_CODE"));
		}
		
		con.close();

		return	 productVO;
	}

	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCTS";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PRODUCT_NO ='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where PRODUCT_NAME='" + searchVO.getSearchKeyword()
						+ "'";
			}
		}
		sql += " order by PRODUCT_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("�ο�:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
					vo.setFileName(rs.getString("FILE_NAME"));
					vo.setManuDate(rs.getString("MANU_DATE"));
					vo.setPrice(rs.getInt("Price"));
					vo.setProdDetail(rs.getString("PROD_DETAIL"));
					vo.setProdName(rs.getString("PROD_Name"));
					vo.setProdNo(rs.getInt("PROD_NO"));
					vo.setRegDate(rs.getDate("REG_DATE"));
					vo.setProTranCode(rs.getString("PRO_TRAN_CODE"));

					list.add(vo);
					if (!rs.next())
						break;
 			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
           
		con.close();
			
		return map;
	}

	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCTS set PRODUCT_NAME=?, PRODUCT DETAIL=?,MANUDATE=?, PRICE=? where PRODUCT_NAME=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.executeUpdate();
		
		con.close();
	}
	
}	
