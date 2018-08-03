/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Customer;
import entities.Orders;
import entities.Product;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author weizy
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class OrdersFacade extends AbstractFacade<Orders> {
	@PersistenceContext(unitName = "OnlineShoppingPU")
	private EntityManager em;
	
	@Resource
    private UserTransaction ut;


	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public OrdersFacade() {
		super(Orders.class);
	}
	//加入购物车但没购买的商品
	public List<Object> getUserOrders(String customerId){
		Query query;
		query = em.createQuery("select p,o from Product p,Orders o where o.productId.id=p.id and o.customerId.id=:customerId and o.state=:state");
		query.setParameter("customerId", customerId);
		query.setParameter("state", "未购买");
		List orderList = query.getResultList();
		
		return orderList;
	}
	//已经付款但没确认收货的商品
	public List<Object> getUnreceivedOrder(String customerId){
		Query query;
		query = em.createQuery("select p,o from Product p,Orders o where o.productId.id=p.id and o.customerId.id=:customerId and o.state=:state");
		query.setParameter("customerId", customerId);
		query.setParameter("state", "待收货");
		List orderList = query.getResultList();
		return orderList;
	}
	//历史订单 已付款的 或 已退订的
	public List<Object> getHistoryOrders(String customerId){
		Query query = em.createQuery("select p,o from Product p,Orders o where o.productId.id=p.id and o.customerId.id=:customerId and (o.state=:state1 or o.state=:state2)");
		query.setParameter("customerId", customerId);
		query.setParameter("state1", "已验收");
		query.setParameter("state2", "已退订");
		return query.getResultList();
	}
	//修改订单数量 +1
	public boolean addOrders(Integer number) {	//订单号
		boolean success = true;
		try {
			ut.begin();
			Query query = em.createQuery("update Orders o set o.amount=o.amount+1 where o.number=:num");
			query.setParameter("num", number);
			int executeUpdate = query.executeUpdate();
			if(executeUpdate==0)
				success = false;
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
				ut.rollback();
			}
		} catch (NotSupportedException | SystemException ex) {
			try {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException ex1) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
		
		return success;
	}
	//修改订单数量 -1
	public boolean subOrders(Integer number){
		boolean success = true;
		try {
			ut.begin();
			Query query = em.createQuery("update Orders o set o.amount = o.amount-1 where o.number=:num");
			query.setParameter("num", number);
			int executeUpdate = query.executeUpdate();
			System.out.println("OrderFacade.java:" + executeUpdate);
			if(executeUpdate==0){
				System.out.println("OrdersFacade.java :00000000000000000000000000");
				success = false;
			}
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
				ut.rollback();
			}
		} catch (NotSupportedException | SystemException ex) {
			try {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException ex1) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
		
		return success;
	}
	//修改订单发送地址
	public boolean modifyOrderAddress(Integer number,String phone,String address){
		System.out.println("OrderFacade.java:modifyOrderAddress:   test01");
		boolean success = true;
		//UPDATE `shopping`.`orders` SET `phone`='123456789', `address`='guangxi' WHERE `number`='1';
		//电话、地址、下订单的时间
		Date date = new Date(System.currentTimeMillis());
		Query query = em.createQuery("update Orders o set o.phone=:phone,o.address=:address,o.state=:state,o.createTime=:date where o.number=:number");
		System.out.println("OrderFacade.java:modifyOrderAddress:   test02");
		query.setParameter("phone", phone);
		query.setParameter("address", address);
		query.setParameter("state", "待收货");	//修改订单状态
		query.setParameter("date", date);
		query.setParameter("number", number);
		System.out.println("OrderFacade.java:modifyOrderAddress:   test03");
		int excuteUpdate = query.executeUpdate();
		if(excuteUpdate==0){
			System.out.println("OrderFacade.java:modifyOrderAddress:   test04");
			success = false;
		}
		System.out.println("OrderFacade.java:modifyOrderAddress:   test05");
		return success;
	}
	//购买
	//订单号，产品号,店铺id，订单包含的数量,店铺收益...
	public boolean purchaseProcessing(String customerId,Integer number,Integer productId,String storeId,int amount,BigDecimal profit,String phone,String address){
		boolean success = true;
		Query query;
		try {
			System.out.println("OrderFacade.java:purchaseProcessing:   test1");
			ut.begin();
			//修改订单的phone、address以及state
			this.modifyOrderAddress(number, phone, address);
			
			//减少产品的数量
			//UPDATE `shopping`.`product` SET `stock`='9' WHERE `id`='1';
			System.out.println("OrderFacade.java:purchaseProcessing:   test2");
			query = em.createQuery("update Product p set p.stock = p.stock-:amount where p.id=:productId");
			query.setParameter("amount", amount);
			query.setParameter("productId", productId);
			query.executeUpdate();
			System.out.println("OrderFacade.java:purchaseProcessing:   test3");
			
			//增加店铺的收益
			//UPDATE `shopping`.`store` SET `income`='6' WHERE `store_id`='123';
			query = em.createQuery("update Store s set s.income=s.income+:profit where s.storeId=:storeId");
			query.setParameter("profit", profit);
			query.setParameter("storeId", storeId);
			query.executeUpdate();
			System.out.println("OrderFacade.java:purchaseProcessing:   test4");
			
			//顾客的账号余额减少
			query = em.createQuery("update Customer c set c.balance=c.balance-:profit where c.id=:customerId");
			query.setParameter("profit", profit);
			query.setParameter("customerId", customerId);
			query.executeUpdate();
			ut.commit();
			System.out.println("OrderFacade.java:purchaseProcessing:   test5");
		} catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
			try {
				System.out.println("OrderFacade.java:purchaseProcessing:   test6");
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		System.out.println("OrderFacade.java:purchaseProcessing:   test7");
		return success;
	}
	public List<Orders> findOrders(Customer customer,Product product){
		Query query = em.createQuery("select o from Orders o where o.customerId=:customerId and o.productId=:productId and o.state=:state");
		query.setParameter("customerId", customer);
		query.setParameter("productId", product);
		query.setParameter("state", "未购买");
		return query.getResultList();
	}
	public void createOrder(Orders order){
		try {
			ut.begin();
			em.persist(order);
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (NotSupportedException | SystemException ex) {
			Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public boolean addAmount(Integer number,int amounts) {	//订单号
		boolean success = true;
		try {
			ut.begin();
			Query query = em.createQuery("update Orders o set o.amount=o.amount+:amounts where o.number=:num");
			query.setParameter("amounts", amounts);
			query.setParameter("num", number);
			int executeUpdate = query.executeUpdate();
			if(executeUpdate==0)
				success = false;
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
				ut.rollback();
			}
		} catch (NotSupportedException | SystemException ex) {
			try {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException ex1) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
		
		return success;
	}
	//确认收货
	public void confirnOrder(Integer orderId){
		try {
			ut.begin();
			//Query query = em.createQuery("update Orders o set o.amount=o.amount+:amounts where o.number=:num");
			Query query = em.createQuery("update Orders o set o.state=:state where o.number=:orderId");
			query.setParameter("state", "已验收");
			query.setParameter("orderId", orderId);
			query.executeUpdate();
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (NotSupportedException | SystemException ex) {
			Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	//退订
	public void deleteUnreceiveOrder(String customerId,Integer productId,Integer orderId,String storeId,int amount,BigDecimal profit){
		try {
			ut.begin();
			//改变订单状态
			Query query = em.createQuery("update Orders o set o.state=:state where o.number=:orderId");
			query.setParameter("state", "已退订");
			query.setParameter("orderId", orderId);
			query.executeUpdate();
			//客户余额增加
			query = em.createQuery("update Customer c set c.balance=c.balance+:profit where c.id=:customerId");
			query.setParameter("profit", profit);
			query.setParameter("customerId", customerId);
			query.executeUpdate();
			//对应店铺收益减少
			query = em.createQuery("update Store s set s.income=s.income-:profit where s.storeId=:storeId");
			query.setParameter("profit", profit);
			query.setParameter("storeId", storeId);
			query.executeUpdate();
			//对应产品数量增加
			query = em.createQuery("update Product p set p.stock=p.stock+:amount where p.id=:productId");
			query.setParameter("amount", amount);
			query.setParameter("productId", productId);
			query.executeUpdate();
			
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (NotSupportedException | SystemException ex) {
			Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void deleteOrder(Integer orderId){
		try {
			ut.begin();
			//改变订单状态
			Query query = em.createQuery("update Orders o set o.state=:state where o.number=:orderId");
			query.setParameter("state", "已退订");
			query.setParameter("orderId", orderId);
			query.executeUpdate();
			//余额增加
			
			//对应店铺收益减少
			
			//对应产品数量增加
			
			try {
				ut.commit();
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (NotSupportedException | SystemException ex) {
			Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	//删除购物车订单
	public void deleteShoppingCarOrder(Integer orderId){
		System.out.println("OrderFacade.java: deleteShoppingCarOrder1:" + orderId);
		try {
			ut.begin();
			System.out.println("OrderFacade.java: deleteShoppingCarOrder2:" + orderId);
			Query query = em.createQuery("delete from Orders o where o.number=:orderId");
			query.setParameter("orderId", orderId);
			query.executeUpdate();
			try {
				System.out.println("OrderFacade.java: deleteShoppingCarOrder3:" + orderId);
				ut.commit();
				System.out.println("OrderFacade.java: deleteShoppingCarOrder4:" + orderId);
			} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
				Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (NotSupportedException | SystemException ex) {
			Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
