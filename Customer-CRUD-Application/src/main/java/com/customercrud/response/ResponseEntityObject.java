package com.customercrud.response;

import lombok.Data;

public @Data class ResponseEntityObject<T> {
	private boolean status;
	private String message;
	private T object;
	private Long totalItems;
	public ResponseEntityObject(boolean status, String message, T object, Long totalItems) {
		this.status = status;
		this.message = message;
		this.object = object;
		this.totalItems = totalItems;
	}
	
	
}