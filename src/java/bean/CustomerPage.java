/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Customer;
import entities.Orders;
import entities.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.CustomerFacade;
import session.OrdersFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@SessionScoped
public class CustomerPage {
	private String name = "未登录";
	private String state = "login.xhtml";
	
	private String id;
	private String password;
	
	private String registerId;
	private String registerPassword;
	private String registerPasswordSure;
	private String registerName;
	private boolean whetherRepeat = false;
	private boolean identical = true;
	
	private String fromPage = "index";
	
	
	private String temp;
	
	Customer cs;
	
	@EJB
	private CustomerFacade customerFacade;
	
	@EJB
	private OrdersFacade ordersFacade;

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterPassword() {
		return registerPassword;
	}

	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}

	public String getRegisterPasswordSure() {
		return registerPasswordSure;
	}

	public void setRegisterPasswordSure(String registerPasswordSure) {
		this.registerPasswordSure = registerPasswordSure;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public boolean isWhetherRepeat() {
		return whetherRepeat;
	}

	public void setWhetherRepeat(boolean whetherRepeat) {
		this.whetherRepeat = whetherRepeat;
	}

	public boolean isIdentical() {
		return identical;
	}

	public void setIdentical(boolean identical) {
		this.identical = identical;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		//this.name = id;
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	

	/**
	 * Creates a new instance of CustomerPage
	 */
	public CustomerPage() {
		this.orderList = new ArrayList();
		this.productList = new ArrayList();
		
	}
	public void clear(){
		this.orderList.clear();
		this.productList.clear();
	}
	public String returnstate(){
		if(this.name.equals("未登录")){
			return "login.xhtml";
		}else{
			return "customerInfo.xhtml";
		}
	}
	//登陆检查
	public String check(){
		List<Customer> customers = customerFacade.findCustomer(id, password);
		if(customers.isEmpty()){
			this.name = "未登录";
			this.temp = "账号或密码错误";
			return "login.xhtml";
		}else{
			this.name = customers.get(0).getName();
		}
		return this.fromPage;
	}
	public void clearData(){
		this.registerId = "";
		this.registerName = "";
		this.registerPassword = "";
		this.registerPasswordSure = "";
		this.whetherRepeat = false;
		this.identical = true;
	}
	//注册检查
	public String registerCheck(){
		//先判断两次密码是否一致
		if(!this.registerPassword.equals(this.registerPasswordSure)){
			this.identical = false;
			return "register";
		}
		
		Customer customer = customerFacade.find(this.registerId);
		if(customer==null){ //账户不重复 
			//写入数据库中 
			Customer newCustomer = new Customer();
			newCustomer.setId(this.registerId);
			newCustomer.setPassword(this.registerPassword);
			newCustomer.setName(this.registerName);
			newCustomer.setBalance(BigDecimal.ZERO);
			customerFacade.create(newCustomer);
			
			this.name = this.registerName;
			this.id = this.registerId;
			this.password = this.registerPassword;
			return "index";
		}else{
			this.whetherRepeat = true;
			return "register";
		}
		
	}
	public String shoppingCart(){
		if(this.name==null){
			return "login";
		}else if(this.name.equals("未登录")){
			return "login";
		}else{
			return "shoppingCart";
		}
	}
	private List<Orders> orderList;
	private List<Product> productList;
	//待收货
	public void unReciveOrder(){
		this.clear();
		List<Object> list = ordersFacade.getUnreceivedOrder(this.id);
		for (Object list1 : list) {
			Object[] o = (Object[])list1;
			this.productList.add((Product) o[0]);
			this.orderList.add((Orders)o[1]);
		}
	}
	//获取历史订单
	public void historyOrder(){
		this.clear();
		List<Object> list = ordersFacade.getHistoryOrders(this.id);
		for(Object list1 : list){
			Object[] o = (Object[])list1;
			this.productList.add((Product) o[0]);
			this.orderList.add((Orders)o[1]);
		}
	}
	public int getAmount(String index){
		int i = Integer.parseInt(index);
		return this.orderList.get(i).getAmount();
	}
	public String getOrderState(String index){
		int i = Integer.parseInt(index);
		return this.orderList.get(i).getState();
	}
	public Integer getOrderId(String index){
		int i = Integer.parseInt(index);
		return this.orderList.get(i).getNumber();
	}
	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	private String delete = "notDelect";
	private String confirm = "notConfirm";
	//没用了 
	public String deleteOrder(String orderId){
		//do some thing
		Integer o_id = Integer.parseInt(orderId);
		ordersFacade.deleteOrder(o_id);
		this.delete = "退货";
		return "customerInfo";
	}
	//退货
	public String deleteUnreceiveOrder(String index){
		int i = Integer.parseInt(index);
		//订单号，产品号,店铺id，订单包含的数量,店铺收益...
		Integer productId = this.productList.get(i).getId();
		Integer orderId = this.orderList.get(i).getNumber();
		String storeId = this.productList.get(i).getStoreId().getStoreId();
		int amount = this.orderList.get(i).getAmount();
		BigDecimal profit = new BigDecimal(this.productList.get(i).getPrice()*amount);
		this.ordersFacade.deleteUnreceiveOrder(this.id,productId, orderId, storeId, amount, profit);
		this.delete = "退货";	//辅助页面出现弹窗
		return "customerInfo";
	}
	public String confirmOrder(String orderId){
		//do some thing
		Integer o_id = Integer.parseInt(orderId);
		this.ordersFacade.confirnOrder(o_id);
		this.confirm = "确认";
		return "customerInfo";
	}

	public String getDelete() {
		String temp2 = this.delete;
		this.delete = "notDelete";
		return temp2;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}
	

	public String getConfirm() {
		String temp2 = this.confirm;
		this.confirm = "notConfirm";
		return temp2;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	private String navigation = "personalInfo.xhtml";
	public String navigatedTo(String to){
		this.navigation = to;
		return "customerInfo";
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	private String reName;
	private String reGender;
	private String reEmail;
	private String rePhone;
	private String reAddress;
	private BigDecimal reBalance;
	private String rePassword;
	private Customer customer;
	private boolean resetSuccess = false;
	
	public String resetCustomerInfo(){
		Customer c = new Customer();
		c.setId(this.id);
		c.setName(this.reName);
		c.setGender(this.reGender);
		c.setEmail(this.reEmail);
		c.setPhone(this.rePhone);
		c.setBalance(this.reBalance);
		c.setPassword(this.customer.getPassword());
		c.setAddress(this.reAddress);
		customerFacade.edit(c);
		this.name = this.reName;
		this.resetSuccess = true;
		return "customerInfo";
		
	}
	public void findCustomer(){
		this.customer = this.customerFacade.find(this.id);
	}

	public String getReName() {
		this.reName = this.customer.getName();
		return reName;
	}

	public void setReName(String reName) {
		this.reName = reName;
	}

	public String getReGender() {
		this.reGender = this.customer.getGender();
		return reGender;
	}

	public void setReGender(String reGender) {
		this.reGender = reGender;
	}

	public String getReEmail() {
		this.reEmail = this.customer.getEmail();
		return reEmail;
	}

	public void setReEmail(String reEmail) {
		this.reEmail = reEmail;
	}

	public String getRePhone() {
		this.rePhone = this.customer.getPhone();
		return rePhone;
	}

	public void setRePhone(String rePhone) {
		this.rePhone = rePhone;
	}

	public String getReAddress() {
		this.reAddress = this.customer.getAddress();
		return reAddress;
	}

	public void setReAddress(String reAddress) {
		this.reAddress = reAddress;
	}

	public BigDecimal getReBalance() {
		this.reBalance = this.customer.getBalance();
		return reBalance;
	}

	public void setReBalance(BigDecimal reBalance) {
		this.reBalance = reBalance;
	}

	public String getRePassword() {
		this.rePassword = this.customer.getPassword();
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public boolean isResetSuccess() {
		boolean temp3 = this.resetSuccess;
		this.resetSuccess = false;
		return temp3;
	}

	public void setReserSuccess(boolean resetSuccess) {
		this.resetSuccess = resetSuccess;
	}
	public String signOut(){
		this.name = "未登录";
		this.id = "";
		this.password = "";
		return "index";
	}
	
	


}
