package br.com.messagebuddy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.messagebuddy.entity.Role;
import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.mail.EmailSender;
import br.com.messagebuddy.repository.MembershipRepository;
import br.com.messagebuddy.repository.RoleRepository;
import br.com.messagebuddy.repository.UserRepository;
import br.com.messagebuddy.util.UserEdit;
import br.com.messagebuddy.util.UserSearchForm;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmailSender emailSender;
	
	@Autowired
	MembershipRepository memberRepository;
	
	@Override
	public void saveUser(User user) {		
		user.setSignupDate(new Date());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findRoleById("ROLE_USER"));
		user.setRoles(roles);
		//emailSender.sendMessage(user.getEmail(), "Welcome", "Welcome to our system, " + user.getName() + "! Your username is: " + user.getUsername());
				
		userRepository.save(user);		
	}
	
	@Override
	public Iterable<User> listUsers() {
		Iterable<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User loadUser(String username) {
		User user = userRepository.findUserByUsername(username);
		return user;
	}
	
	@Override
	public UserEdit loadUserEdit(String username) {
		User user = userRepository.findUserByUsername(username);
		UserEdit userEdit = new UserEdit();
		userEdit.setBirthday(user.getBirthday());
		userEdit.setCity(user.getCity());
		userEdit.setCountry(user.getCountry());
		userEdit.setEmail(user.getEmail());
		userEdit.setFirstName(user.getFirstName());
		userEdit.setLastName(user.getLastName());
		return userEdit;
	}
	
	@Override
	public void saveEditedUser(UserEdit userEdit, String username) {
		User user = userRepository.findUserByUsername(username);
		user.setBirthday(userEdit.getBirthday());
		user.setCity(userEdit.getCity());
		user.setCountry(userEdit.getCountry());
		user.setEmail(userEdit.getEmail());
		user.setFirstName(userEdit.getFirstName());
		user.setLastName(userEdit.getLastName());
		userRepository.save(user);
	}

	/**
	 * We only want the users that weren't invited yet in our result.
	 * So, after we get the resultList from the repository, we create a new List and add the Users that don't have a Member relationship with the Conversation.
	 */
	@Override
	public List<User> searchUser(UserSearchForm userSearch) {
		Iterable<User> resultList = userRepository.findAllUsersByCustomSearch(userSearch);
		
		List<User> users = new ArrayList<User>();
		  
		Iterator<User> i = resultList.iterator();		  
		while(i.hasNext()) {
			User u = i.next();
			if(!memberRepository.existsByUserAndConversation(u, userSearch.getConversation())) {
				users.add(u);
			}
		}
		
		return users;
	}

	

	

}
