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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import session.ProductFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@RequestScoped
public class MainPage {
	@ManagedProperty(value="#{param.pageId}")
	private String pageId = "startPage.xhtml";
	
	@ManagedProperty(value="#{param.category}")
	private String category;
	
	@EJB
	ProductFacade productfacade;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		if(pageId==null){
			pageId = "startPage.xhtml";
		}
		this.pageId = pageId;
	}

	/**
	 * Creates a new instance of MainPage
	 */
	public MainPage() {
	}
	
	public List<Product> getProducts(){
		return productfacade.getProducts(category);
	}
	
	
}
