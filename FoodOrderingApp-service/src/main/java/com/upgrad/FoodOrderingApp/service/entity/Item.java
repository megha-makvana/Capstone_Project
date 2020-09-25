package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="item_name")
	private String itemName;

	private Integer price;

	private String type;

	private String uuid;

	//bi-directional many-to-one association to CategoryItem
	@OneToMany(mappedBy="item")
	private List<CategoryItem> categoryItems;

	//bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy="item")
	private List<OrderItem> orderItems;

	//bi-directional many-to-one association to RestaurantItem
	@OneToMany(mappedBy="item")
	private List<RestaurantItem> restaurantItems;

	public Item() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<CategoryItem> getCategoryItems() {
		return this.categoryItems;
	}

	public void setCategoryItems(List<CategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
	}

	public CategoryItem addCategoryItem(CategoryItem categoryItem) {
		getCategoryItems().add(categoryItem);
		categoryItem.setItem(this);

		return categoryItem;
	}

	public CategoryItem removeCategoryItem(CategoryItem categoryItem) {
		getCategoryItems().remove(categoryItem);
		categoryItem.setItem(null);

		return categoryItem;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setItem(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setItem(null);

		return orderItem;
	}

	public List<RestaurantItem> getRestaurantItems() {
		return this.restaurantItems;
	}

	public void setRestaurantItems(List<RestaurantItem> restaurantItems) {
		this.restaurantItems = restaurantItems;
	}

	public RestaurantItem addRestaurantItem(RestaurantItem restaurantItem) {
		getRestaurantItems().add(restaurantItem);
		restaurantItem.setItem(this);

		return restaurantItem;
	}

	public RestaurantItem removeRestaurantItem(RestaurantItem restaurantItem) {
		getRestaurantItems().remove(restaurantItem);
		restaurantItem.setItem(null);

		return restaurantItem;
	}

}