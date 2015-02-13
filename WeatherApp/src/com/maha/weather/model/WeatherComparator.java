package com.maha.weather.model;

import java.util.Comparator;

public class WeatherComparator implements Comparator<WeatherReport>{

	SortTypes sortType;
	
	private SortTypes compare_mode = sortType.COMPARE_BY_NAME;
	
	public WeatherComparator() {
		// TODO Auto-generated constructor stub
	}
	
	public WeatherComparator(SortTypes mode){
		compare_mode = mode;
	}
	
	@Override
	public int compare(WeatherReport w1, WeatherReport w2) {
		// TODO Auto-generated method stub
		switch (this.compare_mode) {
		case COMPARE_BY_WIND:
			if (w1.getWind().getSpeed() < w2.getWind().getSpeed()) return -1;
			if (w1.getWind().getSpeed() == w2.getWind().getSpeed()) return 0;
			return 1;
		case COMPARE_BY_PRESSURE:
			if (w1.getMain().getPressure() < w2.getMain().getPressure()) return -1;
			if (w1.getMain().getPressure() == w2.getMain().getPressure()) return 0;
			return 1;
		case COMPARE_BY_HUMIDITY:
			if (w1.getMain().getHumidity() < w2.getMain().getHumidity()) return -1;
			if (w1.getMain().getHumidity() == w2.getMain().getHumidity()) return 0;
			return 1;
		default:
			return w1.getName().compareTo(w2.getName());
		}
	}
	
}
