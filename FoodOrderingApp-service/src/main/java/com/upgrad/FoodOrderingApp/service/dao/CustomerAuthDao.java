package com.upgrad.FoodOrderingApp.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;

@Repository
public class CustomerAuthDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerAuth createAuthToken(final CustomerAuth customerAuthEntity) {
		try {
			entityManager.persist(customerAuthEntity);
		} catch (Exception e) {
			System.out.println("DB Error, couldn't get customer auth details");
			e.printStackTrace();
		}
		return customerAuthEntity;
	}

	public CustomerAuth updateAuthToken(final CustomerAuth customerAuthEntity) {
		try {
			entityManager.merge(customerAuthEntity);
		} catch (Exception e) {
			System.out.println("DB Error, couldn't get customer auth details");
			e.printStackTrace();
		}
		return customerAuthEntity;
	}

	public CustomerAuth getAuthTokenEntityByAccessToken(final String accessToken) {
		try {
			return entityManager.createNamedQuery("customerAuthTokenByAccessToken", CustomerAuth.class)
					.setParameter("accessToken", accessToken).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			System.out.println("DB Error, couldn't get customer auth details");
			e.printStackTrace();
			return null;
		}
	}
}
