package com.maxaramos.inventorysystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 9215741738738381538L;

	@Id
	@Column(name = "authority")
	private String authority;

	public Role() {
		super();
	}

	public Role(String authority) {
		this.authority = authority;
	}

	public Role(GrantedAuthority grantedAuthority) {
		authority = grantedAuthority.getAuthority();
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
