package com.upgrad.FoodOrderingApp.service.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the category_item database table.
 * 
 */
@Entity
@Table(name="category_item")
@NamedQuery(name="CategoryItem.findAll", query="SELECT c FROM CategoryItem c")
public class CategoryItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;

	//bi-directional many-to-one association to Item
	@ManyToOne
	private Item item;

	public CategoryItem() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}