package com.maha.weather.utils;

import java.lang.reflect.Method;

public class WeatherUtility {
	public static Method getMethodByName(String name, Class methodClass) {
		StringBuilder methodName = new StringBuilder("get");
		methodName.append(name);
		Method method = null;
		try {
			method = methodClass.getMethod(methodName.toString(), null);
		} catch (SecurityException e) {
			System.err.println(e.getMessage());
		} catch (NoSuchMethodException e) {
			System.err.println(e.getMessage());
		}
		return method;
	}
}
