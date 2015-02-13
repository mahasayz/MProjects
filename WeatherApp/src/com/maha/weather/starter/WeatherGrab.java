package com.maha.weather.starter;

import java.net.URI;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maha.weather.model.WeatherReport;
import com.maha.weather.model.WeatherResults;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class WeatherGrab implements Runnable {

	private Gson gson;
	private WebResource service;
	private String cityParam;
	private WeatherResults weather;

	public WeatherGrab(String city) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(getBaseURI());

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		gson = builder.create();

		cityParam = city;
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://api.openweathermap.org").build();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String resp = service.path("data").path("2.5").path("find")
				.queryParam("q", this.cityParam)
				.queryParam("units", "metric")
				.queryParam("type", "like")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
//		System.out.println(resp);
		weather = gson.fromJson(resp, WeatherResults.class);
		WeatherReport weatherReport;
		if (weather.getCount() > 1) {
			for (int i = 0; i < weather.getCount(); i++) {
				weatherReport = weather.getList().get(i);
				System.out.println((i+1) + ". " + weatherReport.getName() + ", "
						+ weatherReport.getSys().getCountry());
			}
		}
		WeatherRunner.weatherResults.add(weather);
	}

}
