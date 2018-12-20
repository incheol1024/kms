package com.devworker.kms.dic;

public enum LikeType {
	LIKE(1),
	UNLIKE(1);
	
	private int likeValue;
	
	LikeType(int likeValue) {
		this.likeValue = likeValue;
	}
	
	public int getLikeValue() {
		return likeValue;
	}
	
}
