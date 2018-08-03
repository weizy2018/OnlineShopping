/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author weizy
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {
	@PersistenceContext(unitName = "OnlineShoppingPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CustomerFacade() {
		super(Customer.class);
	}
	public List<Customer> findCustomer(String id,String password){
		Query query = em.createQuery("select c from Customer c where c.id=:id and c.password=:password");
		query.setParameter("id", id);
		query.setParameter("password", password);
		return query.getResultList();
	}
	public List<Customer> getCustomerBalance(String customerId){
		Query query = em.createQuery("select c from Customer c where c.id=:id");
		query.setParameter("id", customerId);
		return query.getResultList();
	}
	
}
