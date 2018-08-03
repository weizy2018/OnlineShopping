/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author weizy
 */
@ManagedBean
@RequestScoped
public class Navigation {
	
	@ManagedProperty(value="#{param.pageId}")
	private String pageId;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * Creates a new instance of Navigation
	 */
	public Navigation() {
	}
	public String changePage(){
		System.out.println("Navigation.java:" + pageId);
		if(pageId.equals("page1")){
			return "page1";
		}else{
			return "page2";
		}
	}
	
}
