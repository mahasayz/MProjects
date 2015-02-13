package com.maha.weather.starter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import com.maha.weather.model.SortTypes;
import com.maha.weather.model.WeatherComparator;
import com.maha.weather.model.WeatherReport;
import com.maha.weather.model.WeatherResults;
import com.maha.weather.utils.WeatherUtility;

public class WeatherRunner {

	private static List<WeatherReport> weatherList;
	public static List<WeatherResults> weatherResults;
	private static List<String> searchFields;
	private String[] cities;
	private SortTypes sortType = SortTypes.COMPARE_BY_NAME;
	private Properties props;

	public WeatherRunner(String[] args) {
		parseArgs(args);
		initialize();
	}

	private void initialize() {
		props = new Properties();
		weatherList = new ArrayList<WeatherReport>();
		weatherResults = new ArrayList<WeatherResults>();
		searchFields = new ArrayList<String>();
		searchFields.add("Name");
		try {
			props.load(new FileInputStream("weather-conf.props"));
			Enumeration e = props.propertyNames();

			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				boolean cond = Boolean.parseBoolean(props.getProperty(key));
				if (cond)
					searchFields.add(key);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	private int getMax(final String column) {
		@SuppressWarnings("unchecked")
		Collection<Integer> sizes = CollectionUtils.collect(weatherList,
				new Transformer() {

					public Object transform(Object input) {
						// TODO Auto-generated method stub
						WeatherReport rep = (WeatherReport) input;
						StringBuilder ret = new StringBuilder();
						try {
							Method method = WeatherUtility.getMethodByName(
									column, WeatherReport.class);
							ret.append(method.invoke(rep, null));
						} catch (IllegalArgumentException e) {
							System.err.println(e.getMessage());
						} catch (IllegalAccessException e) {
							System.err.println(e.getMessage());
						} catch (InvocationTargetException e) {
							System.err.println(e.getMessage());
						}
						return Integer.valueOf(ret.length());
					}

				});

		int max = Collections.max(sizes);
		if (max < 15)
			return 15;
		else
			return max;
	}

	private void displayResults() {
		boolean multiple = false;
		for (int i = 0; i < weatherResults.size(); i++) {
			if (weatherResults.get(i).getCount() > 1)
				multiple = true;
			weatherList.add(weatherResults.get(i).getList().get(0));
		}

		if (multiple)
			System.out
					.println("Your search returned multiple results, showing first result in each case");

		Collections.sort(weatherList, new WeatherComparator(sortType));

		DateFormat df = new SimpleDateFormat("HH:mm");
		DecimalFormat decF = new DecimalFormat("###.##");

		StringBuilder formatter = new StringBuilder();
		StringBuilder divider = new StringBuilder();
		int max = 0;
		for (int i = 0; i < searchFields.size(); i++) {
			max = getMax(searchFields.get(i));
			formatter.append("%-").append(max).append("s ");
			for (int j = 0; j < max; j++)
				divider.append("-");
			if (i != (searchFields.size() - 1))
				divider.append(" ");
		}
		formatter.append("%n");
		System.out.println(String.format(formatter.toString(),
				searchFields.toArray()));

		System.out.println(divider);

		WeatherReport weatherReport;
		List<Object> results = new ArrayList<Object>();
		StringBuilder methodName = new StringBuilder();
		StringBuilder returnType = new StringBuilder();
		for (int i = 0; i < weatherList.size(); i++) {
			weatherReport = weatherList.get(i);

			Iterator iterator = searchFields.iterator();
			while (iterator.hasNext()) {
				Method method;
				try {
					method = WeatherUtility.getMethodByName(
							(String) iterator.next(), WeatherReport.class);
					returnType.append(method.getReturnType().getSimpleName());
					if (returnType.toString().equals("Date")) {
						Date result = (Date) method.invoke(weatherReport, null);
						results.add(df.format(result));
					} else if (returnType.toString().equals("double"))
						results.add(decF.format((Double) method.invoke(
								weatherReport, null)));
					else
						results.add(method.invoke(weatherReport, null));
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				} catch (IllegalAccessException e) {
					System.err.println(e.getMessage());
				} catch (InvocationTargetException e) {
					System.err.println(e.getMessage());
				}
				methodName.setLength(0);
				returnType.setLength(0);
			}
			System.out.println(String.format(formatter.toString(),
					results.toArray()));
			results.clear();
		}
	}

	private void parseArgs(String[] args) {
		String lastArg = args[args.length - 1];
		String value;
		if (args.length == 1 && args[0].equals("--help")) {
			System.out
					.print("Usage: java -jar WeatherApp.jar [ city1 city2 ... cityN ] [--sort=name|humidity|pressure|temp]\n\t\t[ --help ]");
			System.exit(0);
		}
		for (int i = 0; i < (args.length - 1); i++)
			if (args[i].indexOf("sort=") >= 0) {
				System.out.println("ERR: Improper positioning of sort flag");
				System.exit(-1);
			}
		if (lastArg.length() > 5 && lastArg.substring(0, 6).equals("--sort")
				&& (lastArg.indexOf("=") >= 0)) {
			value = lastArg.substring(lastArg.indexOf("=") + 1,
					lastArg.length());
			sortType = SortTypes.valueof(value.toLowerCase());
			cities = Arrays.copyOfRange(args, 0, args.length - 1);
		} else {
			System.out
					.println("INFO: No sort criteria/value provided, sorting by default (Name)");
			cities = args;
		}
	}

	public static void main(String[] args) {
		WeatherRunner runner = new WeatherRunner(args);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < runner.cities.length; i++) {
			executorService.execute(new WeatherGrab(runner.cities[i]));
		}
		executorService.shutdown();
		System.out.print("Search in Progress ");
		int i = 1;
		while (!executorService.isTerminated()) {
			if (i % 3 == 0) {
				System.out.print("\b\b\b");
			} else {
				System.out.print(".");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("");
		runner.displayResults();
	}

}
