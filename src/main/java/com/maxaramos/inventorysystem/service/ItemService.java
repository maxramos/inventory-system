package com.maxaramos.inventorysystem.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import com.maxaramos.inventorysystem.model.Item;

@Service
public class ItemService {

	@Autowired
	private Logger log;

	public Item findItem() {
		Item item = getItem();
		log.info(item.toString());
		return item;
	}

	@Lookup
	protected Item getItem() {
		throw new IllegalStateException("Unable to lookup.");
	}

}
