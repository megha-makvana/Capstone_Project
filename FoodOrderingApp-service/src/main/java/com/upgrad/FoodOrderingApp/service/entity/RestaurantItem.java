package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the restaurant_item database table.
 * 
 */
@Entity
@Table(name="restaurant_item")
@NamedQuery(name="RestaurantItem.findAll", query="SELECT r FROM RestaurantItem r")
public class RestaurantItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to Item
	@ManyToOne
	private Item item;

	//bi-directional many-to-one association to Restaurant
	@ManyToOne
	private Restaurant restaurant;

	public RestaurantItem() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}