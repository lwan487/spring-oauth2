/**
 * 
 */
package com.surendra.oauth.common.security;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.surendra.oauth.common.enums.Role;
import com.surendra.oauth.dao.persistence.User;
import com.surendra.oauth.services.IUserService;

/**
 * @author surendra.singh
 *
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

	private IUserService userService;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal() != null ? authentication.getPrincipal().toString() : null;
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        try {
            final User user = this.userService.authenticate(username, password);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            		username, password, Arrays.<GrantedAuthority>asList(new SimpleGrantedAuthority(Role.ROLE_USER.name())));
            token.setDetails(user);
            return token;
        } catch (Exception e) {
            throw new OAuth2Exception(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
