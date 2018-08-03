/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Product;
import entities.Store;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import session.ProductFacade;
import session.StoreFacade;

/**
 *
 * @author weizy
 */
@ManagedBean
@RequestScoped
public class MyStore {
	@EJB
	private ProductFacade productFacade;
	@EJB
	private StoreFacade storeFacade;
	
	private String storeName;
	private String intruduction;
	private BigDecimal income;
	private Date createTime;
	
	private boolean repeat = false;

	/**
	 * Creates a new instance of MyStore
	 */
	public MyStore() {
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getIntruduction() {
		return intruduction;
	}

	public void setIntruduction(String intruduction) {
		this.intruduction = intruduction;
	}

	public boolean isRepeat() {
		return this.repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	//判断用户是否已经注册商店
	public String judge(String customerId){
		System.out.println("MyStore.java:judge:" + customerId);
		Store store = storeFacade.find(customerId);
		if(store==null){
			return "noStore";
		}else{
			return "myStore";
		}
	}
	public List<Product> storeProducts(String customerId){
		//storeId 与 CustomerId是一致的
		return this.productFacade.searchAllFromStoreByStoreId(customerId);
	}
	public String getStoreName(String customerId){
		Store store = storeFacade.find(customerId);
		return store.getName();
	}
	public String register(String customerId){
		//查找店铺名称是否与别人重复
		List<Store> storeList = storeFacade.getStoreByName(this.storeName);
		if(storeList.isEmpty()){ //不重复
			//进行注册
			Store store = new Store();
			store.setStoreId(customerId);
			store.setName(this.storeName);
			store.setIncome(BigDecimal.ZERO);
			store.setCreationTime(new Date(System.currentTimeMillis()));
			store.setIntroduction(this.intruduction);
			storeFacade.create(store);
			return "myStore";
		}else{	//重复
			this.repeat = true;
			return "registerStore";
		}
	}
	public void storeInfo(String customerId){
		Store store = storeFacade.find(customerId);
		this.storeName = store.getName();
		this.income = store.getIncome();
		this.createTime = store.getCreationTime();
		this.intruduction = store.getIntroduction();
	}
	
}
