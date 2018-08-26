package com.maxaramos.inventorysystem.model;

public class Product {

	private String name;

	public Product(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Product [name=%s]", name);
	}

}
