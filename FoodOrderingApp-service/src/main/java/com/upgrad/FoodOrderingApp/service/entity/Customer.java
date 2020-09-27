package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name = "customer", schema = "public")
@NamedQueries({
		@NamedQuery(name = "customerByContactNumber", query = "select c from Customer c where c.contactNumber = :contactNumber") })

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "contact_number", unique = true)
	@NotNull
	@Size(max = 30)
	private String contactNumber;

	@Column(name = "email")
	@Size(max = 50)
	private String email;

	@Column(name = "firstname")
	@NotNull
	@Size(max = 30)
	private String firstname;

	@Column(name = "lastname")
	@Size(max = 30)
	private String lastname;

	@Column(name = "password")
	@NotNull
	@Size(max = 255)
	private String password;

	@Column(name = "salt")
	@NotNull
	@Size(max = 255)
	private String salt;

	@Column(name = "uuid", unique = true)
	@Size(max = 200)
	private String uuid;

	// bi-directional many-to-one association to CustomerAddress
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<CustomerAddress> customerAddresses = new ArrayList<>();

	// bi-directional many-to-one association to CustomerAuth
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<CustomerAuth> customerAuths = new ArrayList<>();

	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastName) {
		this.lastname = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public boolean equals(Object obj) {
		return new EqualsBuilder().append(this, obj).isEquals();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}