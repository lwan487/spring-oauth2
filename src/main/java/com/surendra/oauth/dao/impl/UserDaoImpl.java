/**
 * 
 */
package com.surendra.oauth.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.surendra.oauth.dao.IUserDao;
import com.surendra.oauth.dao.persistence.User;

/**
 * @author surendra.singh
 *
 */
public class UserDaoImpl implements IUserDao {

	private MongoTemplate mongoTemplate;
	
	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.IUserDao#findByEmailAddress(java.lang.String)
	 */
	@Override
	public User findByEmailId(String email) {
		Query query = new Query().addCriteria(new Criteria("emailAddress").is(email));
		return this.mongoTemplate.findOne(query, User.class);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.IUserDao#findAll()
	 */
	@Override
	public List<User> findAll() {
		return this.mongoTemplate.findAll(User.class);
	}
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.IUserDao#createUser(com.surendra.oauth.dao.persistence.User)
	 */
	@Override
	public boolean createUser(User user) {
		mongoTemplate.save(user);;
		return true;
	}
}
