package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the customer_auth database table.
 * 
 */
@Entity
@Table(name="customer_auth")
@NamedQuery(name="CustomerAuth.findAll", query="SELECT c FROM CustomerAuth c")
public class CustomerAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="access_token")
	private String accessToken;

	@Column(name="expires_at")
	private Timestamp expiresAt;

	@Column(name="login_at")
	private Timestamp loginAt;

	@Column(name="logout_at")
	private Timestamp logoutAt;

	private String uuid;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer customer;

	public CustomerAuth() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Timestamp getExpiresAt() {
		return this.expiresAt;
	}

	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Timestamp getLoginAt() {
		return this.loginAt;
	}

	public void setLoginAt(Timestamp loginAt) {
		this.loginAt = loginAt;
	}

	public Timestamp getLogoutAt() {
		return this.logoutAt;
	}

	public void setLogoutAt(Timestamp logoutAt) {
		this.logoutAt = logoutAt;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}