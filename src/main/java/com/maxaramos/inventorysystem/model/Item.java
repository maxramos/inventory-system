package com.maxaramos.inventorysystem.model;

public class Item {

	private String name;
	private long count;

	public Item(String name, long count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return String.format("Item [name=%s, count=%s]", name, count);
	}

}
