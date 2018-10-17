package com.devworker.kms.util;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonUtil {
	private CommonUtil() {}
	
	public static <T> List<T> iterToList(Iterable<T> iter){
		List<T> list = new ArrayList<>();
		for(T t : iter) {
			list.add(t);
		}
		return list;
	}
}
