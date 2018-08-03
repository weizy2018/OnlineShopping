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
import javax.faces.bean.RequestScoped;
import session.ProductFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@RequestScoped
public class InitialPage {
	@EJB
	private ProductFacade product;

	/**
	 * Creates a new instance of InitialPage
	 */
	public InitialPage() {
	}
	public List<Product> getAllProducts(){
		return product.findAll();
	}
	
}
