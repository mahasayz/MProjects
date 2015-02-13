package com.maha.weather.tester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import com.maha.weather.model.WeatherReport;

public class Tester {
	
	public static void old(String[] args) {
List<String> longFlags = new ArrayList<String>();
		
		longFlags.add("--help");
		longFlags.add("--sort");
		
		
		Map<String, String> flags = new HashMap<String, String>();
		for (int i=0; i<args.length; i++){
			if (args[i].length() > 2 && args[i].substring(0, 2).equals("--")){
				if (args[i].contains("=")){
					flags.put(args[i].substring(0, args[i].indexOf('=')), 
							args[i].substring(args[i].indexOf('=')+1, args[i].length()));
				} else {
					flags.put(args[i], null);
				}
			} else if (args[i].charAt(0) == '-'){
				flags.put(args[i], args[++i]);
			} else {
				
			}
		}

		Iterator iterator;
		Map countryMap = new TreeMap<String, String>();
		for (String code : java.util.Locale.getISOCountries())
			countryMap.put(code, new Locale("", code).getDisplayCountry());
		
		Set<String> keySet = countryMap.keySet();
		iterator = keySet.iterator();
		while(iterator.hasNext())
			System.out.println(countryMap.get(iterator.next()));
		System.out.println(countryMap.get("CA"));
		
		printMemberClasses(WeatherReport.class);
		
		
	}
	
	public static void main(String[] args){
		int[] sizes = {10, 15, 10, 15, 12, 15};
		String[] headers = {"Wind", "Temperature", "Pressure", "Humidity", "Condition", "Co-ords"};
		
		StringBuilder formatter = new StringBuilder();
		for (int i=0; i<sizes.length; i++){
			formatter.append("%-").append(sizes[i]).append("s ");
			if (i+1 == sizes.length)
				formatter.append("%n");
		}
		System.out.println(String.format(formatter.toString(), headers));
		Tester test = new Tester();
		try {
			Method method = Tester.class.getMethod("getDate", null);
			System.out.println(method.getReturnType().getSimpleName());
			StringBuilder sb = new StringBuilder();
			sb.append("Date");
			if (sb.toString().equals("Date"))
				System.out.println("We got DATE");
			DateFormat df = new SimpleDateFormat("HH:mm");
			System.out.println(df.format((Date)method.invoke(test, null)));
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Date getDate(){
		return new Date();
	}
	
	public static void printMemberClasses(final Class c){
		final Class[] nestedClasses = c.getClasses();
		final Class[] decNested = c.getDeclaredClasses();
		System.out.println(c.getName());
		System.out.println(Arrays.asList(nestedClasses));
		System.out.println(Arrays.asList(decNested));
	}
}
