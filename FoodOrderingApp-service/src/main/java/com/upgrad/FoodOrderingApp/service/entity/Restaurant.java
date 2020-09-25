package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the restaurant database table.
 * 
 */
@Entity
@NamedQuery(name="Restaurant.findAll", query="SELECT r FROM Restaurant r")
public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="average_price_for_two")
	private Integer averagePriceForTwo;

	@Column(name="customer_rating")
	private BigDecimal customerRating;

	@Column(name="number_of_customers_rated")
	private Integer numberOfCustomersRated;

	@Column(name="photo_url")
	private String photoUrl;

	@Column(name="restaurant_name")
	private String restaurantName;

	private String uuid;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="restaurant")
	private List<Order> orders;

	//bi-directional many-to-one association to Address
	@ManyToOne
	private Address address;

	//bi-directional many-to-one association to RestaurantCategory
	@OneToMany(mappedBy="restaurant")
	private List<RestaurantCategory> restaurantCategories;

	//bi-directional many-to-one association to RestaurantItem
	@OneToMany(mappedBy="restaurant")
	private List<RestaurantItem> restaurantItems;

	public Restaurant() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAveragePriceForTwo() {
		return this.averagePriceForTwo;
	}

	public void setAveragePriceForTwo(Integer averagePriceForTwo) {
		this.averagePriceForTwo = averagePriceForTwo;
	}

	public BigDecimal getCustomerRating() {
		return this.customerRating;
	}

	public void setCustomerRating(BigDecimal customerRating) {
		this.customerRating = customerRating;
	}

	public Integer getNumberOfCustomersRated() {
		return this.numberOfCustomersRated;
	}

	public void setNumberOfCustomersRated(Integer numberOfCustomersRated) {
		this.numberOfCustomersRated = numberOfCustomersRated;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getRestaurantName() {
		return this.restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setRestaurant(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setRestaurant(null);

		return order;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<RestaurantCategory> getRestaurantCategories() {
		return this.restaurantCategories;
	}

	public void setRestaurantCategories(List<RestaurantCategory> restaurantCategories) {
		this.restaurantCategories = restaurantCategories;
	}

	public RestaurantCategory addRestaurantCategory(RestaurantCategory restaurantCategory) {
		getRestaurantCategories().add(restaurantCategory);
		restaurantCategory.setRestaurant(this);

		return restaurantCategory;
	}

	public RestaurantCategory removeRestaurantCategory(RestaurantCategory restaurantCategory) {
		getRestaurantCategories().remove(restaurantCategory);
		restaurantCategory.setRestaurant(null);

		return restaurantCategory;
	}

	public List<RestaurantItem> getRestaurantItems() {
		return this.restaurantItems;
	}

	public void setRestaurantItems(List<RestaurantItem> restaurantItems) {
		this.restaurantItems = restaurantItems;
	}

	public RestaurantItem addRestaurantItem(RestaurantItem restaurantItem) {
		getRestaurantItems().add(restaurantItem);
		restaurantItem.setRestaurant(this);

		return restaurantItem;
	}

	public RestaurantItem removeRestaurantItem(RestaurantItem restaurantItem) {
		getRestaurantItems().remove(restaurantItem);
		restaurantItem.setRestaurant(null);

		return restaurantItem;
	}

}