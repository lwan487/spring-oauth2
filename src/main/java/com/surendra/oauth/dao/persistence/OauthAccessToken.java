/**
 * 
 */
package com.surendra.oauth.dao.persistence;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author surendra.singh
 *
 */
@SuppressWarnings("serial")
@Document(collection = "OauthAccessToken")
public class OauthAccessToken extends BaseEntity {

	private String tokenId;
	private OAuth2AccessToken oAuth2AccessToken;
	private String authenticationId;
	private String userName;
	private String clientId;
	private OAuth2Authentication authentication;
	private String refreshToken;

	public OauthAccessToken() {
		super();
	}

	public OauthAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication, final String authenticationId) {
		this.tokenId = oAuth2AccessToken.getValue();
		this.oAuth2AccessToken = oAuth2AccessToken;
		this.authenticationId = authenticationId;
		this.userName = authentication.getName();
		this.clientId = authentication.getOAuth2Request().getClientId();
		this.authentication = authentication;
		this.refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public OAuth2AccessToken getoAuth2AccessToken() {
		return oAuth2AccessToken;
	}

	public void setoAuth2AccessToken(OAuth2AccessToken oAuth2AccessToken) {
		this.oAuth2AccessToken = oAuth2AccessToken;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public OAuth2Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = authentication;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
