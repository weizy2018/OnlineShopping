/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Customer;
import entities.Orders;
import entities.Product;
import entities.Store;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.CustomerFacade;
import session.OrdersFacade;
import session.StoreFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@SessionScoped
public class ShoppingCart {
	@EJB
	private OrdersFacade orderFacade;
	
	@EJB
	private StoreFacade storeFacade;
	
	@EJB
	private CustomerFacade customerFacade;
	
	private Customer cs;
	
	private String customerId;
	private String orderPhone;

	private String orderAddress;
	
	private ArrayList selectedOrder;	//要购买的购物车列表
	private ArrayList<Integer> selected;		//要购买的购物车列表
	
	private List orderlist;		//从数据库中查找的的订单列表
	
	private ArrayList<Orders> ordersList;
	private ArrayList<Product> productsList;
	
	private int count = 0;
	
	public void findCustomer(){
		this.cs = customerFacade.find(this.customerId);
		this.orderPhone = cs.getPhone();
		this.orderAddress = cs.getAddress();
	}
	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	public Orders getOneOrder(int index){
		return this.ordersList.get(index);
	}
	public Product getOneProduct(int index){
		return this.productsList.get(index);
	}

	public ArrayList<Orders> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(ArrayList<Orders> ordersList) {
		this.ordersList = ordersList;
	}

	public ArrayList<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(ArrayList<Product> productsList) {
		this.productsList = productsList;
	}

	public ArrayList getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(ArrayList selectedOrder) {
		System.out.println("Shooping.java: setSelectedOrder:" + selectedOrder.toString());
		if(!selectedOrder.isEmpty()){
			this.selected.add(Integer.parseInt(selectedOrder.get(0).toString()));	//由于特殊原因只能这样操作了
		}
		this.selectedOrder = selectedOrder;
	}

	public ArrayList<Integer> getSelected() {
		return selected;
	}

	public void setSelected(ArrayList<Integer> selected) {
		this.selected = selected;
	}
	//获取选择订单的照片
	public String getSelectedImg(int index){
		return this.productsList.get(index).getImageAddr();
	}
	//获取选择订单的产品名称
	public String getSelectedName(int index){
		return this.productsList.get(index).getName();
	}
	//获取选择订单的产品描述
	public String getSelectedDescription(int index){
		return this.productsList.get(index).getDescription();
	}
	//获取选择订单的单价
	public double getSelectedPrice(int index){
		return this.productsList.get(index).getPrice();
	}
	//获取选择订单的数量
	public int getSelectedAmount(int index){
		return this.ordersList.get(index).getAmount();
	}
	//获取单个订单的总额
	public double getSumAmount(int index){
		return this.productsList.get(index).getPrice()*this.ordersList.get(index).getAmount();
	}
	//获取选择订单的总价
	public double getTotalAmount(){
		double total = 0.0d;
		for(int i=0;i<this.selected.size();i++){
			System.out.println("ShoppingCart.java: getTotalAmount:for1111:" + this.selected.get(i));
		}
		for (int i = 0;i<this.selected.size();i++) {
			int selectedIndex = this.selected.get(i);
			System.out.println("ShoppingCart.java: getTotalAmount:for:" + this.productsList.get(selectedIndex).getPrice());
			total += this.productsList.get(selectedIndex).getPrice()*this.ordersList.get(selectedIndex).getAmount();
		}
		return total;
	}
	public BigDecimal getCustomerBalance(){
		System.out.println("ShoppingCart.java:getCustomerBalance():" + this.customerId);
		List<Customer> customerList = customerFacade.getCustomerBalance(this.customerId);
		return customerList.get(0).getBalance();
		
	}
	private boolean flag;

	public boolean checkEnough(){
		System.out.println("shoppingCart.java: checkEnouge:" + flag);
		return flag;
	}
	//Integer number,Integer productId,String storeId,int amount,BigDecimal profit,String phone,String address
	public String payForSelected(){
		//先确定余额是否足够
		//然后修改订单状态
		if(this.getCustomerBalance().doubleValue()<this.getTotalAmount()){	//确定余额是否足够
			flag = true;
			return "checkForPayment";
		}else{																//修改订单状态
			for (Integer selected1 : this.selected) {
				int index = selected1;
				Integer number = this.ordersList.get(index).getNumber();	//订单编号
				Integer productId = this.productsList.get(index).getId();//产品Id
				String storeId = this.productsList.get(index).getStoreId().getStoreId();//店铺的ID
				int amount = this.ordersList.get(index).getAmount();//订单包含的个数
				BigDecimal profit = new BigDecimal(this.productsList.get(index).getPrice()*amount);//订单发出后店铺的收益
				//Integer number,Integer productId,String storeId,int amount,BigDecimal profit,String phone,String address
				this.orderFacade.purchaseProcessing(this.customerId,number, productId, storeId, amount, profit, this.orderPhone, this.orderAddress);
			}
			return "index";
		}
	}
	
	//清空之前选择列表
	public void cleared(){
		this.selectedOrder.clear();
		this.selected.clear();
		this.flag = false;
	}
	

	/**
	 * Creates a new instance of ShoppingCart
	 */
	public ShoppingCart() {
		this.selected = new ArrayList();
		this.selectedOrder = new ArrayList();
		this.orderlist = new ArrayList();
		this.ordersList = new ArrayList();
		this.productsList = new ArrayList();
	}
	public void getUserOrders(String customerId){
		this.customerId = customerId;
		this.ordersList.clear();
		this.productsList.clear();
		this.orderlist = orderFacade.getUserOrders(customerId);
		for(Object o:this.orderlist){
			Object[] oo = (Object[])o;
			this.productsList.add((Product)oo[0]);
			this.ordersList.add((Orders)oo[1]);
		}
		//return orderlist;
	}
	//增加个数
	public String addOrder(int a){
		this.count = (count+1)%2;
		if(this.count==1){
			Orders oo = this.ordersList.get(a);
			orderFacade.addOrders(oo.getNumber());
		}
		return "shoppingCart";
	}
	//减少个数
	public String subOrder(int a){
		this.count = (count+1)%2;
		if(this.count==1){
			Orders oo = this.ordersList.get(a);
			if(oo.getAmount()>1){
				orderFacade.subOrders(oo.getNumber());
			}
		}
		return "shoppingCart";
	}
	//删除购物车商品
	//不知为什么，链接点击一次竟然相应两次，所以新建一个变量来控制删除的次数
	boolean tempDel = false;
	public String deleteOrder(String index){
		System.out.println("ShoppintCart.java:deleteOrder:" + index);
		tempDel = !tempDel;
		if(tempDel){
			int i = Integer.parseInt(index);
			Integer orderId = this.ordersList.get(i).getNumber();
			orderFacade.deleteShoppingCarOrder(orderId);
		}
		return "shoppingCart";
	}
	//获取店铺的名字
	public String getStoreName(int index){

		Product p = this.productsList.get(index);
		//通过过商品实体的store_id选择商店
		List<Store> stores = this.storeFacade.getStore(p.getStoreId().getStoreId());
		if(stores.isEmpty()){
			return null;
		}else{
			return stores.get(0).getName();
		}
	}
	//前往支付
	public String goToPay(){
		if(this.selected.isEmpty()){
			return "shoppingCart";
		}else{
			return "checkForPayment";
		}
	}
	
	
}
