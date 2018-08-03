/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Customer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import session.CustomerFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@RequestScoped
public class Test {
	@EJB
	CustomerFacade customer;
	
	private String state = "login.xhtml";
	
	private String data[];

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		//this.list.add(data[0]);
		this.data = data;
	}
	private ArrayList<String> list;

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

//	public String getData() {
//		return data;
//	}
//
//	public void setData(List data) {
//		System.out.print("Test.java:" + data);
//		this.list.add((String) data.get(0));
//	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Creates a new instance of Test
	 */
	public Test() {
		this.list = new ArrayList<>();
	}
	public List<Customer> getAllCustomer(){
		return customer.findAll();
	}
	public String[] getName(){
		String[] names = {"abc","def","ijk"};
		return  names;
	}
	
}
