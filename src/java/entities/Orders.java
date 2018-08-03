/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author weizy
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
	@NamedQuery(name = "Orders.findByNumber", query = "SELECT o FROM Orders o WHERE o.number = :number"),
	@NamedQuery(name = "Orders.findByCreateTime", query = "SELECT o FROM Orders o WHERE o.createTime = :createTime"),
	@NamedQuery(name = "Orders.findByAmount", query = "SELECT o FROM Orders o WHERE o.amount = :amount"),
	@NamedQuery(name = "Orders.findByState", query = "SELECT o FROM Orders o WHERE o.state = :state")})
public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "number")
	private Integer number;
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Customer customerId;
	@JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Product productId;
	@Size(max = 45)
    @Column(name = "phone")
	private String phone;
	@Size(max = 45)
    @Column(name = "address")
	private String address;
	@Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Basic(optional = false)
    @NotNull
    @Column(name = "amount")	
	private int amount;
	@Size(max = 5)
    @Column(name = "state")
	private String state;

	public Orders() {
	}

	public Orders(Integer number) {
		this.number = number;
	}

	public Orders(Integer number, int amount) {
		this.number = number;
		this.amount = amount;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (number != null ? number.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Orders)) {
			return false;
		}
		Orders other = (Orders) object;
		if ((this.number == null && other.number != null) || (this.number != null && !this.number.equals(other.number))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Orders[ number=" + number + " ]";
	}
	
}
