package br.com.messagebuddy.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.util.UserSearchForm;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Iterable<User> findAllUsersByCustomSearch(UserSearchForm userSearch) {
		String queryString = "SELECT u from User u WHERE u."
				+ userSearch.getField() 
				+ " = '"
				+ userSearch.getWord()
				+ "'";
		TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
		return query.getResultList();
		
	}

}
