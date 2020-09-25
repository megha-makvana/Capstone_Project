package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="category_name")
	private String categoryName;

	private String uuid;

	//bi-directional many-to-one association to CategoryItem
	@OneToMany(mappedBy="category")
	private List<CategoryItem> categoryItems;

	//bi-directional many-to-one association to RestaurantCategory
	@OneToMany(mappedBy="category")
	private List<RestaurantCategory> restaurantCategories;

	public Category() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
		categoryItem.setCategory(this);

		return categoryItem;
	}

	public CategoryItem removeCategoryItem(CategoryItem categoryItem) {
		getCategoryItems().remove(categoryItem);
		categoryItem.setCategory(null);

		return categoryItem;
	}

	public List<RestaurantCategory> getRestaurantCategories() {
		return this.restaurantCategories;
	}

	public void setRestaurantCategories(List<RestaurantCategory> restaurantCategories) {
		this.restaurantCategories = restaurantCategories;
	}

	public RestaurantCategory addRestaurantCategory(RestaurantCategory restaurantCategory) {
		getRestaurantCategories().add(restaurantCategory);
		restaurantCategory.setCategory(this);

		return restaurantCategory;
	}

	public RestaurantCategory removeRestaurantCategory(RestaurantCategory restaurantCategory) {
		getRestaurantCategories().remove(restaurantCategory);
		restaurantCategory.setCategory(null);

		return restaurantCategory;
	}

}