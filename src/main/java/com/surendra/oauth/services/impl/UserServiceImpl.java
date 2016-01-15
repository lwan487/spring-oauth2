/**
 * 
 */
package com.surendra.oauth.services.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.surendra.oauth.common.Constant;
import com.surendra.oauth.dao.IUserDao;
import com.surendra.oauth.dao.persistence.User;
import com.surendra.oauth.services.IUserService;

/**
 * @author surendra.singh
 *
 */
public class UserServiceImpl implements IUserService, UserDetailsService {

	private Md5PasswordEncoder passwordEncoder;
	 
	private IUserDao userDao;
	 
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return locateUser(email);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.services.IUserService#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public User authenticate(String email, String password) {
		Assert.notNull(email);
		Assert.notNull(password);
		User user = locateUser(email);
		if (!passwordEncoder.encodePassword(password, Constant.SALT).equals(user.getPassword())) {
			throw new UsernameNotFoundException("failed to locate a user");
		}
		return user;
	}

	private User locateUser(final String email) {
		notNull(email, "Mandatory argument 'email' missing.");
		User user = findByEmailId(email);
		if (user == null) {
			throw new UsernameNotFoundException("failed to locate a user");
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.services.IUserService#findById(java.lang.String)
	 */
	@Override
	public User findByEmailId(String email) {
		Assert.notNull(email);
		User user = userDao.findByEmailId(email.toLowerCase());
		if(user == null) {
            throw new UsernameNotFoundException("failed to locate a user");
        }
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.surendra.oauth.services.IUserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.surendra.oauth.services.IUserService#createUser(com.surendra.oauth.dao.persistence.User)
	 */
	@Override
	public boolean createUser(User user) {
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(), Constant.SALT));
		return userDao.createUser(user);
	}
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
