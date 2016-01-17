package com.surendra.oauth.dao.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mongodb.DBObject;
import com.surendra.oauth.common.enums.Role;

/**
 * @author surendra.singh
 *
 */
@Document(collection = "User")
@SuppressWarnings({"serial", "unchecked"})
public class User  extends BaseEntity implements UserDetails {

	@NotBlank(message = "User Name is mandatory")
	private String name;
	private Integer age;
	
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	private List<Role> roles = new ArrayList<Role>();

	public User() {
		super();
	}

	public User(String id) {
		super(id);
	}

	public User(DBObject dbObject) {
		this((String) dbObject.get("_id"));
		this.email = (String) dbObject.get("email");
		this.name = (String) dbObject.get("name");
		this.password = (String) dbObject.get("password");
		List<String> roles = (List<String>) dbObject.get("roles");
		deSerializeRoles(roles);
	}

	private void deSerializeRoles(List<String> roles) {
		for (String role : roles) {
			this.addRole(Role.valueOf(role));
		}
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : this.getRoles()) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
			authorities.add(authority);
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return Collections.unmodifiableList(this.roles);
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
