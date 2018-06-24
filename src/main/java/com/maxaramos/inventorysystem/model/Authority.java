package com.maxaramos.inventorysystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 9215741738738381538L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "authority")
	private String authority;

	public Authority() {
		super();
	}

	public Authority(String authority) {
		this.authority = authority;
	}

	public Authority(GrantedAuthority grantedAuthority) {
		authority = grantedAuthority.getAuthority();
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
