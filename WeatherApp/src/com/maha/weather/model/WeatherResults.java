package com.maha.weather.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherResults {
	String message;
	String cod;
	int count;
	List<WeatherReport> list = new ArrayList<WeatherReport>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<WeatherReport> getList() {
		return list;
	}

	public void setList(List<WeatherReport> list) {
		this.list = list;
	}
}
