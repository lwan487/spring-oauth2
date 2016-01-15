package com.surendra.oauth.dao;

import java.util.List;

import com.surendra.oauth.dao.persistence.OauthAccessToken;
import com.surendra.oauth.dao.persistence.OauthRefreshToken;

/**
 * @author surendra.singh
 *
 */
public interface ITokenStoreDao {

	OauthAccessToken findAccessTokenByTokenId(String tokenId);

	void saveAccessToken(OauthAccessToken accessToken);

	void deleteAccessToken(OauthAccessToken accessToken);

	OauthRefreshToken findRefreshTokenByTokenId(String tokenId);
	
	void saveRefreshToken(OauthRefreshToken refreshToken);

	void deleteRefreshToken(OauthRefreshToken refreshToken);

	OauthAccessToken findAccessTokenByRefreshToken(String refreshToken);

	OauthAccessToken findByAuthenticationId(String authenticationId);

	List<OauthAccessToken> findAccessTokenByClientId(String clientId);

	List<OauthAccessToken> findAccessTokenByClientIdAndUserName(String clientId, String userName);
}
