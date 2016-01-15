/**
 * 
 */
package com.surendra.oauth.common.security;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.surendra.oauth.common.enums.Role;
import com.surendra.oauth.dao.persistence.User;
import com.surendra.oauth.services.IUserService;

/**
 * @author surendra.singh
 *
 */
@Controller
@RequestMapping("/user")
public class UserSignup {
	
	private IUserService userService;

	@RequestMapping(value = "/login", produces = {MediaType.TEXT_HTML_VALUE}, method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/signup", produces = {MediaType.TEXT_HTML_VALUE}, method = RequestMethod.GET)
	public ModelAndView signupPage() {
		return new ModelAndView("signup");
	}

	@ResponseBody
	@RequestMapping(value = "/userExistForEmail/{email}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public boolean validateEmail(@PathVariable String email) {
		try {
			this.userService.findByEmailId(email);
		} catch (UsernameNotFoundException e) {
			return false;
		}
        return true; 
    }
	
	@ResponseBody
	@RequestMapping(value = "/createUser", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public void createUser(@RequestParam String email, @RequestParam String password, 
    		@RequestParam String name, HttpServletResponse response) throws IOException {
		User user = new User();
		user.setEmailAddress(email);
		user.setPassword(password);
		user.setName(name);
		user.addRole(Role.ROLE_USER);
        this.userService.createUser(user);
        
        final String redirectUrl = "../oauth/token?client_id=353b302c44574f&client_secret=286924697e615a6"
        		+ "&username=%1$s&password=%2$s&grant_type=password";
        response.sendRedirect(String.format(redirectUrl, email, password));
    }
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
