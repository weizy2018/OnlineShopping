/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author weizy
 */
@ManagedBean
@RequestScoped
public class PayForSelected {

	private String[] selectedProduct;	//要购买的购物车列表 由于特殊原因，一次只能加入一个，故需要再定义一个列表用于保存所有的已选订单
	private ArrayList<String> selected;	//保存属性为订单编号

	public ArrayList<String> getSelected() {
		return selected;
	}

	public void setSelected(ArrayList<String> selected) {
		this.selected = selected;
	}

	public String[] getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(String[] selectedProduct) {
//		if(selectedProduct.length!=0){
//			this.selected.add(selectedProduct[0]);
//		}
		this.selectedProduct = selectedProduct;
	}
	
	public PayForSelected() {
		this.selected = new ArrayList();
	}
	
}
