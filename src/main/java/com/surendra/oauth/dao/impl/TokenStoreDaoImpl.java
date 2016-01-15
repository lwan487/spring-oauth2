/**
 * 
 */
package com.surendra.oauth.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.surendra.oauth.dao.ITokenStoreDao;
import com.surendra.oauth.dao.persistence.OauthAccessToken;
import com.surendra.oauth.dao.persistence.OauthRefreshToken;

/**
 * @author surendra.singh
 *
 */
public class TokenStoreDaoImpl implements ITokenStoreDao {

	private MongoTemplate mongoTemplate;
	
	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#findAccessTokenByTokenId(java.lang.String)
	 */
	@Override
	public OauthAccessToken findAccessTokenByTokenId(String tokenId) {
		Query query = new Query().addCriteria(new Criteria("tokenId").is(tokenId));
		return mongoTemplate.findOne(query, OauthAccessToken.class);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#saveAccessToken(com.surendra.oauth.dao.persistence.OAuth2AuthenticationAccessToken)
	 */
	@Override
	public void saveAccessToken(OauthAccessToken accessToken) {
		mongoTemplate.save(accessToken);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#deleteAccessToken(com.surendra.oauth.dao.persistence.OAuth2AuthenticationAccessToken)
	 */
	@Override
	public void deleteAccessToken(OauthAccessToken accessToken) {
		mongoTemplate.remove(accessToken);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#findRefreshTokenByTokenId(java.lang.String)
	 */
	@Override
	public OauthRefreshToken findRefreshTokenByTokenId(String tokenId) {
		Query query = new Query().addCriteria(new Criteria("tokenId").is(tokenId));
		return mongoTemplate.findOne(query, OauthRefreshToken.class);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#saveRefreshToken(com.surendra.oauth.dao.persistence.OAuth2AuthenticationRefreshToken)
	 */
	@Override
	public void saveRefreshToken(OauthRefreshToken refreshToken) {
		mongoTemplate.save(refreshToken);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#deleteRefreshToken(com.surendra.oauth.dao.persistence.OAuth2AuthenticationRefreshToken)
	 */
	@Override
	public void deleteRefreshToken(OauthRefreshToken refreshToken) {
		mongoTemplate.remove(refreshToken);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#findAccessTokenByRefreshToken(java.lang.String)
	 */
	@Override
	public OauthAccessToken findAccessTokenByRefreshToken(String refreshToken) {
		Query query = new Query().addCriteria(new Criteria("refreshToken").is(refreshToken));
		return mongoTemplate.findOne(query, OauthAccessToken.class);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#findByAuthenticationId(java.lang.String)
	 */
	@Override
	public OauthAccessToken findByAuthenticationId(String authenticationId) {
		Query query = new Query().addCriteria(new Criteria("authenticationId").is(authenticationId));
		return mongoTemplate.findOne(query, OauthAccessToken.class);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#findAccessTokenByClientId(java.lang.String)
	 */
	@Override
	public List<OauthAccessToken> findAccessTokenByClientId(String clientId) {
		Query query = new Query().addCriteria(new Criteria("clientId").is(clientId));
		return mongoTemplate.find(query, OauthAccessToken.class);
	}

	/* (non-Javadoc)
	 * @see com.surendra.oauth.dao.ITokenStoreDao#findAccessTokenByClientIdAndUserName(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OauthAccessToken> findAccessTokenByClientIdAndUserName(String clientId, String userName) {
		Query query = new Query().addCriteria(new Criteria("clientId").is(clientId)).addCriteria(new Criteria("userName").is(userName));
		return mongoTemplate.find(query, OauthAccessToken.class);
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}
