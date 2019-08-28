package br.com.messagebuddy.repository;

import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.util.UserSearchForm;

public interface UserRepositoryCustom {
	
	Iterable<User> findAllUsersByCustomSearch(UserSearchForm userSearch);

}
