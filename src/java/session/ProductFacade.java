/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Product;
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
public class ProductFacade extends AbstractFacade<Product> {
	@PersistenceContext(unitName = "OnlineShoppingPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ProductFacade() {
		super(Product.class);
	}
	public List<Product> getProducts(String category){
		Query query = em.createQuery("select p from Product p where p.category like :category");
		category = "%" + category + "%";
		query.setParameter("category", category);
		return query.getResultList();
	}
	public List<Product> searchProduct(String keyword){
		Query query = em.createQuery("select p from Product p where p.name like :name or p.category like :category");
		keyword = "%" + keyword + "%";
		query.setParameter("name", keyword);
		query.setParameter("category", keyword);
		return query.getResultList();
	}
	//首页查找
	public List<Product> searchProductByType(String type){
		Query query = em.createQuery("select p from Product p where p.name like :type1 or p.category like :type2");
		query.setParameter("type1", type);
		query.setParameter("type2", type);
		return query.setMaxResults(10).getResultList();
	}
	//本店查找
	public List<Product> searchAllFromStore(Store store){
		Query query = em.createQuery("SELECT p FROM Product p WHERE p.storeId = :store");
		query.setParameter("store", store);
		return query.getResultList();
	}
	//通过id查找店铺的所有商品
	public List<Product> searchAllFromStoreByStoreId(String storeId){
		Query query = em.createQuery("SELECT p FROM Product p WHERE p.storeId.storeId = :storeId");
		query.setParameter("storeId", storeId);
		return query.getResultList();
	}
	public List<Product> searchKeywordFromStore(Store store,String keyword){
		Query query = em.createQuery("SELECT p FROM Product p WHERE p.storeId = :store AND (p.category like :keyword1 OR p.name like :keyword2)");
		keyword = "%" + keyword + "%";
		query.setParameter("store", store);
		query.setParameter("keyword1", keyword);
		query.setParameter("keyword2", keyword);
		return query.getResultList();
		
	}
	
}
