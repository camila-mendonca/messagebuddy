package br.com.messagebuddy.service;

import java.util.List;

import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.util.UserEdit;
import br.com.messagebuddy.util.UserSearchForm;

public interface UserService {
	
	public void saveUser(User user);
	public Iterable<User> listUsers();
	public User loadUser(String username);
	public UserEdit loadUserEdit(String username);
	public void saveEditedUser(UserEdit userEdit, String username);
	public List<User> searchUser(UserSearchForm userSearch);

}
