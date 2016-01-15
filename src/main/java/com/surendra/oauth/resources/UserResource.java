package com.surendra.oauth.resources;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.surendra.oauth.dao.persistence.User;
import com.surendra.oauth.services.IUserService;

/**
 * @author surendra.singh
 *
 */
@Controller
@RequestMapping("/v1.0")
public class UserResource {

	private IUserService userService;

	@RequestMapping(value = "/dashboard", produces = {MediaType.TEXT_HTML_VALUE}, method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView loginPage() {
		return new ModelAndView("dashboard");
	}
	
	@ResponseBody
	@RequestMapping(value = "/me", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public User getInfo() {
		final OAuth2Authentication obj = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return (User) obj.getUserAuthentication().getDetails();
    }
	
	@ResponseBody
	@RequestMapping(value = "/user/{email}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public User getUser(@PathVariable String email) {
        return this.userService.findByEmailId(email);
    }
	
	@ResponseBody
	@RequestMapping(value = "/userList", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public List<User> getUserList() {
        return this.userService.findAll();
    }
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
