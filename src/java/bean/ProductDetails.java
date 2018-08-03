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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import session.CustomerFacade;
import session.OrdersFacade;
import session.ProductFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@SessionScoped
public class ProductDetails {
	@EJB
	ProductFacade productFacade;
	@EJB
	CustomerFacade customerFacade;
	@EJB
	OrdersFacade ordersFacade;
	
	private String productId;
	private Product product;
	
	private Store storeId;
	private String keyword;
	
	private int amount = 1;
	private String fromPageName;
	
	private String addSuccess;
	private String noProduct;
	private String way;
	
	private List<Product> productList;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	

	public String setWay(String way) {
		this.way = way;
		return "storePage";
	}

	public Store getStoreId() {
		return storeId;
	}

	public void setStoreId(Store storeId) {
		this.storeId = storeId;
	}
	

	/**
	 * Creates a new instance of productDetails
	 */
	public ProductDetails() {
	}
	
	public Product getProductDetails(){
		return productFacade.find(Integer.parseInt(productId));
	}
	public String showProductDetails(String productsId){
		System.out.println("ProductDeatails.java: showProductDetails" + productsId);
		this.productId = productsId;
		this.product = productFacade.find(Integer.parseInt(productsId));
		this.storeId = this.product.getStoreId();
		return "productDetails";
	}
	public String showStoreProductDetails(String productsId){
		this.modefySuccess = "false";
		this.addSuccess = "false";
		System.out.println("ProductDeatails.java: showProductDetails" + productsId);
		this.productId = productsId;
		this.product = productFacade.find(Integer.parseInt(productsId));
		this.storeId = this.product.getStoreId();
		return "storeProductDetails";
	}

//	public String showStoreProductDetails(String index){
//		for (Product productList1 : productList) {
//			System.out.println("ProductDetails.java:showStoreProductDetails: " + productList1.getId());
//		}
//		int indexs = Integer.parseInt(index);
//		int p_id = this.productList.get(indexs).getId();
//		System.out.println("ProductDetail.java:showStoreProductDetails:  " + indexs +"  " +p_id);
//		this.product = productFacade.find(p_id);
//		this.storeId = this.product.getStoreId();
//		return "productDetails";
//	}
	public int getAmount() {
		if(!this.fromPageName.equals("productDetails")){
			this.amount = 1;
		}
		return amount;
	}

	public String getAddSuccess() {
		System.out.println("ProductDetails.java: getAddSuccess" + this.addSuccess);
		return addSuccess;
	}

	public void setAddSuccess(String addSuccess) {
		this.addSuccess = addSuccess;
	}

	public String getNoProduct() {
		return noProduct;
	}

	public void setNoProduct(String noProduct) {
		this.noProduct = noProduct;
	}
	
	

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String addAmount(){
		if(this.product.getStock()>this.amount){
			this.amount++;
		}
		this.fromPageName = "productDetails";
		return "productDetails";
	}
	public String subAmount(){
		if(this.amount>1){
			this.amount--;
		}
		this.fromPageName = "productDetails";
		return "productDetails";
	}
	public void setFromPage(String pageName){
		this.fromPageName = pageName;
//		return "searchResult";
	}
	public String addToCar(String customerName,String customerId){
		
		if(customerName==null || customerName.equals("未登录")){
			return "login";
		}else if(this.product.getStock()==0){
			this.noProduct = "没货了";
			return "productDetails";
		}else{
			Customer customer = customerFacade.find(customerId);	//根据Id查找顾客
			//查找该顾客是否曾经将此商品加入购物车
			List<Orders> preOrderList = ordersFacade.findOrders(customer, this.product);
			if(!preOrderList.isEmpty()){
				Orders preOrder = preOrderList.get(0);
				//将原来的订单加上相应的数量
				ordersFacade.addAmount(preOrder.getNumber(), this.amount);
			}else{
				Orders order = new Orders();
				order.setCustomerId(customer);
				order.setProductId(this.product);
				order.setPhone(customer.getPhone());
				order.setAddress(customer.getAddress());
				order.setCreateTime(new Date(System.currentTimeMillis()));
				order.setAmount(this.amount);
				order.setState("未购买");
				ordersFacade.createOrder(order);
			}
			this.addSuccess = "添加成功";
			return "productDetails";
		}
	}
	public List<Product> searchAllFromStore(){
		this.productList = this.productFacade.searchAllFromStore(this.storeId);
		return this.productList;
	}
	public List<Product> searchKeywordFromStore(){
		this.productList = this.productFacade.searchKeywordFromStore(this.storeId, this.keyword);
		for (Product productList1 : productList) {
			System.out.println("ProductDetails.java:searchKeywordFromStore:" + productList1.getId());
		}
		return this.productList;
	}
	//点击店铺名称的链接
	public String showStoreAll(){
		this.keyword = "";
		return "storePage";
	}
	private String modefySuccess;

	public String getModefySuccess() {
		return modefySuccess;
	}

	public void setModefySuccess(String modefySuccess) {
		this.modefySuccess = modefySuccess;
	}
	
	public String modifyProductInfo(){
		this.productFacade.edit(this.product);
		this.modefySuccess = "修改成功";
		return "storeProductDetails";
	}
	
	//有问题
//	public List<Product> search(){
//		if(this.way.equals("keyword")){
//			this.way = "notKeyword";
//			return searchKeywordFromStore();
//		}else{
//			return searchAllFromStore();
//		}
//	}
	
}
