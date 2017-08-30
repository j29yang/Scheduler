package com.scm.dashboard.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeSetDTO {
	
	@JsonProperty("items")
	private ItemDTO[] itemDTOS;

	public ItemDTO[] getItemDTOS() {
		return itemDTOS;
	}

	public void setItemDTOS(ItemDTO[] itemDTOS) {
		this.itemDTOS = itemDTOS;
	}
	
}
