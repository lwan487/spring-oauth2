/**
 * 
 */
package com.surendra.oauth.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.surendra.oauth.dao.ITokenStoreDao;
import com.surendra.oauth.dao.persistence.OauthAccessToken;
import com.surendra.oauth.dao.persistence.OauthRefreshToken;

/**
 * @author surendra.singh
 *
 */
public class oAuthMongoTokenStore implements TokenStore {

	private ITokenStoreDao tokenStoreDao;
	
	private AuthenticationKeyGenerator authenticationKeyGenerator;
	 
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#readAuthentication(org.springframework.security.oauth2.common.OAuth2AccessToken)
	 */
	@Override
	public OAuth2Authentication readAuthentication(final OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#readAuthentication(java.lang.String)
	 */
	@Override
	public OAuth2Authentication readAuthentication(final String tokenId) {
		final OauthAccessToken accessToken = tokenStoreDao.findAccessTokenByTokenId(tokenId);
		return accessToken == null ? null : accessToken.getAuthentication();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#storeAccessToken(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public void storeAccessToken(final OAuth2AccessToken token, final OAuth2Authentication authentication) {
		final OauthAccessToken accessToken = new OauthAccessToken(token,
                authentication, authenticationKeyGenerator.extractKey(authentication));
		tokenStoreDao.saveAccessToken(accessToken);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#readAccessToken(java.lang.String)
	 */
	@Override
	public OAuth2AccessToken readAccessToken(final String tokenValue) {
		final OauthAccessToken token = tokenStoreDao.findAccessTokenByTokenId(tokenValue);
        return token == null ? null : token.getoAuth2AccessToken();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#removeAccessToken(org.springframework.security.oauth2.common.OAuth2AccessToken)
	 */
	@Override
	public void removeAccessToken(final OAuth2AccessToken token) {
		final OauthAccessToken accessToken = tokenStoreDao.findAccessTokenByTokenId(token.getValue());
        if(accessToken != null) {
        	tokenStoreDao.deleteAccessToken(accessToken);
        }
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#storeRefreshToken(org.springframework.security.oauth2.common.OAuth2RefreshToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public void storeRefreshToken(final OAuth2RefreshToken refreshToken, final OAuth2Authentication authentication) {
        tokenStoreDao.saveRefreshToken(new OauthRefreshToken(refreshToken, authentication));
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#readRefreshToken(java.lang.String)
	 */
	@Override
	public OAuth2RefreshToken readRefreshToken(final String tokenValue) {
		final OauthRefreshToken refreshToken = tokenStoreDao.findRefreshTokenByTokenId(tokenValue);
		return refreshToken == null ? null : refreshToken.getoAuth2RefreshToken();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#readAuthenticationForRefreshToken(org.springframework.security.oauth2.common.OAuth2RefreshToken)
	 */
	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(final OAuth2RefreshToken token) {
		final OauthRefreshToken refreshToken = tokenStoreDao.findRefreshTokenByTokenId(token.getValue());
		return refreshToken == null ? null : refreshToken.getAuthentication();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#removeRefreshToken(org.springframework.security.oauth2.common.OAuth2RefreshToken)
	 */
	@Override
	public void removeRefreshToken(final OAuth2RefreshToken token) {
		final OauthRefreshToken refreshToken = tokenStoreDao.findRefreshTokenByTokenId(token.getValue());
		if(refreshToken != null) {
			tokenStoreDao.deleteRefreshToken(refreshToken);
        }
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#removeAccessTokenUsingRefreshToken(org.springframework.security.oauth2.common.OAuth2RefreshToken)
	 */
	@Override
	public void removeAccessTokenUsingRefreshToken(final OAuth2RefreshToken refreshToken) {
		final OauthAccessToken accessToken = tokenStoreDao.findAccessTokenByRefreshToken(refreshToken.getValue());
        if(accessToken != null) {
        	tokenStoreDao.deleteAccessToken(accessToken);
        }
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#getAccessToken(org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public OAuth2AccessToken getAccessToken(final OAuth2Authentication authentication) {
		final OauthAccessToken token =  tokenStoreDao.findByAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        return token == null ? null : token.getoAuth2AccessToken();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#findTokensByClientIdAndUserName(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(final String clientId, final String userName) {
        return extractAccessTokens(tokenStoreDao.findAccessTokenByClientIdAndUserName(clientId, userName));
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#findTokensByClientId(java.lang.String)
	 */
	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(final String clientId) {
        return extractAccessTokens(tokenStoreDao.findAccessTokenByClientId(clientId));
	}

	private Collection<OAuth2AccessToken> extractAccessTokens(final List<OauthAccessToken> tokens) {
        final List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
        for(OauthAccessToken token : tokens) {
            accessTokens.add(token.getoAuth2AccessToken());
        }
        return accessTokens;
    }
	
	public void setTokenStoreDao(ITokenStoreDao tokenStoreDao) {
		this.tokenStoreDao = tokenStoreDao;
	}

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}
}
