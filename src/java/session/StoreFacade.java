/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Store;
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
public class StoreFacade extends AbstractFacade<Store> {
	@PersistenceContext(unitName = "OnlineShoppingPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public StoreFacade() {
		super(Store.class);
	}
	public List<Store> getStore(String storeId){
		//select * from store where store_id = '123'
		Query query = em.createQuery("select s from Store s where s.storeId=:storeId");
		query.setParameter("storeId", storeId);
		return query.getResultList();
	}
	public List<Store> getStoreByName(String storeName){
		Query query = em.createQuery("SELECT s FROM Store s WHERE s.name=:storeName");
		query.setParameter("storeName", storeName);
		return query.getResultList();
	}
	
}
