package com.upgrad.FoodOrderingApp.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.upgrad.FoodOrderingApp.service.entity.Customer;

@Repository
public class CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Customer createCustomer(final Customer customerEntity) {
		try {
			entityManager.persist(customerEntity);
		} catch (Exception e) {
			System.out.println("DB Error, could not fetch customer entity");
			e.printStackTrace();
		}
		return customerEntity;
	}

	public Customer getUserByContactNumber(final String contactNumber) {
		try {
			return entityManager.createNamedQuery("customerByContactNumber", Customer.class)
					.setParameter("contactNumber", contactNumber).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception e) {
			System.out.println("DB Error, could not fetch customer entity");
			e.printStackTrace();
			return null;
		}
	}

	public Customer updateCustomer(final Customer customerEntity) {
		try {
			entityManager.merge(customerEntity);
		} catch (Exception e) {
			System.out.println("DB Error, could not fetch customer entity");
			e.printStackTrace();
		}
		return customerEntity;
	}

}
