/**
 * 
 */
package com.surendra.oauth.dao.persistence;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author surendra.singh
 *
 */
@SuppressWarnings("serial")
@Document(collection = "OauthRefreshToken")
public class OauthRefreshToken extends BaseEntity {

	private String tokenId;
	private OAuth2RefreshToken oAuth2RefreshToken;
	private OAuth2Authentication authentication;

	public OauthRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication authentication) {
		this.oAuth2RefreshToken = oAuth2RefreshToken;
		this.authentication = authentication;
		this.tokenId = oAuth2RefreshToken.getValue();
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public OAuth2RefreshToken getoAuth2RefreshToken() {
		return oAuth2RefreshToken;
	}

	public void setoAuth2RefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
		this.oAuth2RefreshToken = oAuth2RefreshToken;
	}

	public OAuth2Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = authentication;
	}
}
