package com.stormmind;

import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Municipality;
import com.stormmind.infrastructure.weather_api.AnaDeArmasFetcher;
import com.stormmind.infrastructure.weather_api.FNNFetcher;
import com.stormmind.infrastructure.weather_api.SydneySweeneyFetcher;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StormmindApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormmindApplication.class, args);
	}

}

/*
@SpringBootApplication
public class StormmindApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StormmindApplication.class, args);

		FNNFetcher fetcher = context.getBean(FNNFetcher.class);
		SydneySweeneyFetcher fetcher2 = context.getBean(SydneySweeneyFetcher.class);
		AnaDeArmasFetcher fetcher3 = context.getBean(AnaDeArmasFetcher.class);

		Municipality m1 = new Municipality("Züri HB", new Coordinates(47.378101F, 8.539364F));
		Municipality m2 = new Municipality("Militär Flugplatz Emmen", new Coordinates(47.092215F, 8.305664F));

		WeatherDataDTO vanilla = fetcher.vanillaFetch(m1, m2);
		WeatherDataDTO sydney = fetcher2.sydneySweeneyFetch(m1, m2);
		WeatherDataDTO ana = fetcher3.anaDeArmasFetch(m1, m2);

		System.out.println("vanilla: "+vanilla);
		System.out.println("sydney: "+sydney);
		System.out.println("ana: "+ana);
	}

}*/