package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer active;

	private String city;

	@Column(name="flat_buil_number")
	private String flatBuilNumber;

	private String locality;

	private String pincode;

	private String uuid;

	//bi-directional many-to-one association to State
	@ManyToOne
	private State state;

	//bi-directional many-to-one association to CustomerAddress
	@OneToMany(mappedBy="address")
	private List<CustomerAddress> customerAddresses;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="address")
	private List<Order> orders;

	//bi-directional many-to-one association to Restaurant
	@OneToMany(mappedBy="address")
	private List<Restaurant> restaurants;

	public Address() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActive() {
		return this.active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFlatBuilNumber() {
		return this.flatBuilNumber;
	}

	public void setFlatBuilNumber(String flatBuilNumber) {
		this.flatBuilNumber = flatBuilNumber;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<CustomerAddress> getCustomerAddresses() {
		return this.customerAddresses;
	}

	public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}

	public CustomerAddress addCustomerAddress(CustomerAddress customerAddress) {
		getCustomerAddresses().add(customerAddress);
		customerAddress.setAddress(this);

		return customerAddress;
	}

	public CustomerAddress removeCustomerAddress(CustomerAddress customerAddress) {
		getCustomerAddresses().remove(customerAddress);
		customerAddress.setAddress(null);

		return customerAddress;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setAddress(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setAddress(null);

		return order;
	}

	public List<Restaurant> getRestaurants() {
		return this.restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Restaurant addRestaurant(Restaurant restaurant) {
		getRestaurants().add(restaurant);
		restaurant.setAddress(this);

		return restaurant;
	}

	public Restaurant removeRestaurant(Restaurant restaurant) {
		getRestaurants().remove(restaurant);
		restaurant.setAddress(null);

		return restaurant;
	}

}