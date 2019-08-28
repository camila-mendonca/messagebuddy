package br.com.messagebuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.messagebuddy.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	
	@Query("SELECT r FROM Role r where r.name = ?1")
	public Role findRoleById(String name);

}
