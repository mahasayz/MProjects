package com.maha.weather.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class WeatherReport {
	class Clouds {
		int all;

		public int getAll() {
			return all;
		}
	}
	
	class Coordinates {
		private double lon;
		private double lat;

		public double getLon() {
			return lon;
		}

		public double getLat() {
			return lat;
		}
	}
	
	class Main {
		double temp;
		int humidity;
		double pressure;
		double temp_min;
		double temp_max;

		public double getTemp() {
			return temp;
		}

		public int getHumidity() {
			return humidity;
		}

		public double getPressure() {
			return pressure;
		}

		public double getTemp_min() {
			return temp_min;
		}

		public double getTemp_max() {
			return temp_max;
		}
	}
	
	class Sys {
		private String country;
		private long sunrise;
		private long sunset;

		public String getCountry() {
			return country;
		}

		public long getSunrise() {
			return sunrise;
		}

		public long getSunset() {
			return sunset;
		}
	}
	
	class Weather {
		long id;
		String main;
		String description;
		String icon;

		public long getId() {
			return id;
		}

		public String getMain() {
			return main;
		}

		public String getDescription() {
			return description;
		}

		public String getIcon() {
			return icon;
		}

	}
	
	class Wind {
		double speed;
		double gust;
		double deg;

		public double getSpeed() {
			return speed;
		}

		public double getGust() {
			return gust;
		}

		public double getDeg() {
			return deg;
		}

	}
	
	Coordinates coord;
	Sys sys;
	List<Weather> weather = new ArrayList<Weather>();
	String base;
	Main main;
	Wind wind;
	Clouds clouds;
	long dt;
	long id;
	String name;
	private Map<String, String> countryMap;

	public WeatherReport() {
		countryMap = new TreeMap<String, String>();
		for (String code : java.util.Locale.getISOCountries())
			countryMap.put(code, new Locale("", code).getDisplayCountry());
	}

	public String getCoord() {
		DecimalFormat df = new DecimalFormat("###.##");
		return new StringBuilder().append("[")
				.append(df.format(this.coord.getLat())).append(", ")
				.append(df.format(this.coord.getLon())).append("]").toString();
	}

	public Date getSunrise() {
		return new Date(this.sys.getSunrise() * 1000);
	}

	public Date getSunset() {
		return new Date(this.sys.getSunset() * 1000);
	}

	public String getCondition() {
		return weather.get(0).getDescription();
	}
	
	public String getIcon() {
		return weather.get(0).getIcon();
	}

	public String getBase() {
		return base;
	}

	public String getCountry() {
		String result = (this.sys.getCountry().length() > 2) ? this.sys
				.getCountry() : this.countryMap.get(this.sys.getCountry());
		return result;
	}

	public Sys getSys() {
		return sys;
	}

	public Main getMain() {
		return main;
	}

	public Wind getWind() {
		return wind;
	}

	public double getTemp() {
		double temp = (this.main.getTemp() > 273.15)
				? (this.main.getTemp() - 273.15)
				: (this.main.getTemp());
		return temp;
	}

	public double getTempMin() {
		double temp = (this.main.getTemp_min() > 273.15)
				? (this.main.getTemp_min() - 273.15)
				: (this.main.getTemp_min());
		return temp;
	}

	public double getTempMax() {
		double temp = (this.main.getTemp_max() > 273.15)
				? (this.main.getTemp_max() - 273.15)
				: (this.main.getTemp_max());
		return temp;
	}

	public double getHumidity() {
		return this.main.getHumidity();
	}

	public double getPressure() {
		return this.main.getPressure();
	}

	public double getSpeed() {
		return this.wind.getSpeed();
	}

	public double getGust() {
		return this.wind.getGust();
	}

	public Clouds getClouds() {
		return clouds;
	}

	public Date getDt() {
		return new Date(this.dt * 1000);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
