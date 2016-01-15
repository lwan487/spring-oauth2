/**
 * 
 */
package com.surendra.oauth.services;

import java.util.List;

import com.surendra.oauth.dao.persistence.User;

/**
 * @author surendra.singh
 *
 */
public interface IUserService {

	User authenticate(String userName, String password);
	
	User findByEmailId(String email);

	List<User> findAll();

	boolean createUser(User user);
}
