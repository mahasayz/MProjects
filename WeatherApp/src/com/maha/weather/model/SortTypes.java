package com.maha.weather.model;

public enum SortTypes {
	COMPARE_BY_NAME		("name"), 
	COMPARE_BY_TEMP		("temp"), 
	COMPARE_BY_PRESSURE	("pressure"), 
	COMPARE_BY_HUMIDITY	("humidity");
	
	private String type;
	private SortTypes(String type){
		this.type = type;
	}
	
	static public SortTypes valueof (String value) {
		for (SortTypes types : SortTypes.values ()) {
			if (types.type.equals(value))
				return types;
		}
		return SortTypes.COMPARE_BY_NAME;
	}
}
