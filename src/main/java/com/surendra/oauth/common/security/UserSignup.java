/**
 * 
 */
package com.surendra.oauth.common.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class UserSignup implements Validator {
	
	private IUserService userService;
	
	private LocalValidatorFactoryBean validator;

	@RequestMapping(value = "/login", produces = {MediaType.TEXT_HTML_VALUE}, method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/signup", produces = {MediaType.TEXT_HTML_VALUE}, method = RequestMethod.GET)
	public ModelAndView signupPage() {
		return new ModelAndView("signup");
	}

	@ResponseBody
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public  Map<String, String> createUser(@Valid User user, BindingResult result, HttpServletResponse response) throws IOException {
		if (result.hasErrors()) {
			response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			return getErrorMap(result.getFieldErrors());
		}
		user.addRole(Role.ROLE_USER);
        this.userService.createUser(user);
        return Collections.emptyMap();
    }
	
	private Map<String, String> getErrorMap(List<FieldError> errorList) {
		Map<String, String> errorMap = new HashMap<String, String> ();
		for (FieldError fieldError : errorList) {
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return errorMap;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(this);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		validator.validate(target, errors);		
		User user = (User) target;
		if (!errors.hasFieldErrors("email") && isEmailRegistered(user.getEmail())) {
			errors.rejectValue("email", "duplicate", "Email already registered");
		}
	}
	
	private boolean isEmailRegistered(final String email) {
		try {
			this.userService.findByEmailId(email);
		} catch (UsernameNotFoundException e) {
			return false;
		}
        return true; 
    }
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setValidator(LocalValidatorFactoryBean validator) {
		this.validator = validator;
	}
}
