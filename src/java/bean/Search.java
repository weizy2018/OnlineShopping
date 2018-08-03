/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.ProductFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@SessionScoped
public class Search {
	private String keyword;
	
	@EJB
	ProductFacade productFacade;
	
	private List<Product> productList;
	
	

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	

	
	public Search() {
	}
	//搜索栏搜索
	public List<Product> searchProduct(){
		this.productList = productFacade.searchProduct(this.keyword);
		for (Product productList1 : this.productList) {
			System.out.println("Search: searchrodct:" + productList1);
		}
		return this.productList;
	}
	public String getResultPage(){
		return "searchResult";
	}
	//菜单栏搜索
	public String setCategoryKeyword(String category){
		this.keyword = category;
		return "searchResult";
	}
	
	//主页排版查询
	public List<Product> searchByType(String type){
		return productFacade.searchProductByType(type);
	}
	public List<Product> getAllProducts(){
		return productFacade.findAll();
	}
	
}
