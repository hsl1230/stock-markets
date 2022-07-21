package com.example.stockmarkets.rest.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.stockmarkets.StockMarketsApplication;
import com.example.stockmarkets.repository.DowJonesIndexRepository;
import com.example.stockmarkets.service.CsvFileService;
import com.example.stockmarkets.service.DowJonesIndexService;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { StockMarketsApplication.class,
		DowJonesIndexController.class })
public class DowJonesIndexControllerApiTest {
	private static final String CONTENT_TYPE = "application/json";
	@Value("${local.server.port}")
	private int port;

	@Autowired
	@InjectMocks
	private DowJonesIndexService dowJonesIndexService;

	@Mock
	private DowJonesIndexRepository dowJonesIndexRepository;

	@Autowired
	@InjectMocks
	private CsvFileService csvFileService;

	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		MockitoAnnotations.openMocks(this);

	}

	@Test
	public void findDowJonesIndicesSuccess() {
		when(dowJonesIndexRepository.findByStock(anyString())).thenReturn(List.of());
		given()
				.contentType(CONTENT_TYPE)
				.queryParam("stock", "AA")
				.when().get("/api/dow-jones-indices")
				.then().log().all().statusCode(HttpStatus.SC_OK);
	}
}
