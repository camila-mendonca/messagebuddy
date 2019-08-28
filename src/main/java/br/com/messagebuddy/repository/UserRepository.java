package br.com.messagebuddy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.messagebuddy.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom{
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findUserByUsername(String username) throws UsernameNotFoundException;

}
