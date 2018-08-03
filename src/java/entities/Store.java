/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author weizy
 */
@Entity
@Table(name = "store")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Store.findAll", query = "SELECT s FROM Store s"),
	@NamedQuery(name = "Store.findByStoreId", query = "SELECT s FROM Store s WHERE s.storeId = :storeId"),
	@NamedQuery(name = "Store.findByName", query = "SELECT s FROM Store s WHERE s.name = :name"),
	@NamedQuery(name = "Store.findByIncome", query = "SELECT s FROM Store s WHERE s.income = :income"),
	@NamedQuery(name = "Store.findByCreationTime", query = "SELECT s FROM Store s WHERE s.creationTime = :creationTime"),
	@NamedQuery(name = "Store.findByIntroduction", query = "SELECT s FROM Store s WHERE s.introduction = :introduction")})
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "store_id")
	private String storeId;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
	private String name;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "income")
	private BigDecimal income;
	@Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	@Size(max = 250)
    @Column(name = "introduction")
	private String introduction;
	@OneToMany(mappedBy = "storeId")
	private Collection<Product> productCollection;
	@JoinColumn(name = "store_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
	private Customer customer;

	public Store() {
	}

	public Store(String storeId) {
		this.storeId = storeId;
	}

	public Store(String storeId, String name) {
		this.storeId = storeId;
		this.name = name;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@XmlTransient
	public Collection<Product> getProductCollection() {
		return productCollection;
	}

	public void setProductCollection(Collection<Product> productCollection) {
		this.productCollection = productCollection;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (storeId != null ? storeId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Store)) {
			return false;
		}
		Store other = (Store) object;
		if ((this.storeId == null && other.storeId != null) || (this.storeId != null && !this.storeId.equals(other.storeId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Store[ storeId=" + storeId + " ]";
	}
	
}
