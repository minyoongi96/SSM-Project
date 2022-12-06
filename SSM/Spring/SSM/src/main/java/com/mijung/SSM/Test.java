package com.mijung.SSM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Object> list = new ArrayList<Object>();
		
		map.put("keyword", "할인");
		map.put("time", 1);
		
		map.put("keyword", "할인");
		map.put("time", 2);
		
		map.put("keyword", "할인");
		map.put("time", 12);
		
		map.put("keyword", "할인");
		map.put("time", 29);
		
		System.out.println(map);
	}
}
