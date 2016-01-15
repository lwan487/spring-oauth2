/**
 * 
 */
package com.surendra.oauth.dao;

import java.util.List;

import com.surendra.oauth.dao.persistence.User;

/**
 * @author surendra.singh
 *
 */
public interface IUserDao {

	User findByEmailId(String email);

	List<User> findAll();

	boolean createUser(User user);
}
