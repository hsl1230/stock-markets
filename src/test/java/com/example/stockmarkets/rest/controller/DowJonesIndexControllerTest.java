package com.example.stockmarkets.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;

import com.example.stockmarkets.document.CreateDowJonesIndexRequest;
import com.example.stockmarkets.document.DowJonesIndex;
import com.example.stockmarkets.message.Response;
import com.example.stockmarkets.service.CsvFileService;
import com.example.stockmarkets.service.DowJonesIndexService;
import com.example.stockmarkets.util.JsonLoaderUtil;
import com.fasterxml.jackson.core.type.TypeReference;

public class DowJonesIndexControllerTest {

	@Mock
	private CsvFileService csvFileService;
	@Mock
	private DowJonesIndexService dowJonesIndexService;

	@InjectMocks
	private DowJonesIndexController dowJonesIndexController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		when(dowJonesIndexService.findDowJonesIndexByStock(anyString()))
				.thenReturn(
						JsonLoaderUtil.getObjectFrom(
								"list-of-dow-jones-index.json",
								new TypeReference<List<DowJonesIndex>>() {
								}));
		when(dowJonesIndexService.deleteDowJonesIndex(anyString()))
				.thenReturn(
						JsonLoaderUtil.getObjectFrom(
								"dow-jones-index.json",
								DowJonesIndex.class));
		when(dowJonesIndexService.saveDowJonesIndex(any(DowJonesIndex.class)))
				.thenReturn(
						JsonLoaderUtil.getObjectFrom(
								"dow-jones-index.json",
								DowJonesIndex.class));
	}

	@Test
	public void testLoadIndexes_failedWithNoFileSelected() throws IOException {
		MockMultipartFile csvFile = new MockMultipartFile(
				"data",
				null,
				"text/plain",
				new byte[0]);
		ResponseEntity<Response> res = this.dowJonesIndexController.loadIndexes(csvFile);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("", res.getBody().getMessages().get(0).getFilename());
		assertEquals("failed", res.getBody().getMessages().get(0).getStatus());
	}

	@Test
	public void testLoadIndexes_successfully() throws IOException {
		doNothing().when(csvFileService).store(any(InputStream.class));

		MockMultipartFile csvFile = new MockMultipartFile(
				"data",
				"dow_jones_index.data",
				"text/plain",
				this.getClass().getResourceAsStream("/text/dow_jones_index.data"));
		ResponseEntity<Response> res = this.dowJonesIndexController.loadIndexes(csvFile);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
		assertEquals("dow_jones_index.data", res.getBody().getMessages().get(0).getFilename());
		assertEquals("succeed", res.getBody().getMessages().get(0).getStatus());
	}

	@Test
	public void testLoadIndexes_failedWithInternalServerError() throws IOException {
		doThrow(new RuntimeException("parse error")).when(csvFileService).store(any(InputStream.class));

		MockMultipartFile csvFile = new MockMultipartFile(
				"data",
				"dow_jones_index.data",
				"text/plain",
				this.getClass().getResourceAsStream("/text/dow_jones_index.data"));
		ResponseEntity<Response> res = this.dowJonesIndexController.loadIndexes(csvFile);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
		assertEquals("dow_jones_index.data", res.getBody().getMessages().get(0).getFilename());
		assertEquals("parse error", res.getBody().getMessages().get(0).getMessage());
		assertEquals("failed", res.getBody().getMessages().get(0).getStatus());
	}

	@Test
	public void testCreateIndex_successfully() {
		CreateDowJonesIndexRequest mockRequest = JsonLoaderUtil.getObjectFrom(
				"create-dow-jones-index-request.json",
				CreateDowJonesIndexRequest.class);
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setRequestURI("/api/dow-jones-indices");
		ResponseEntity<Response> res = this.dowJonesIndexController.createIndex(mockRequest, mockHttpServletRequest);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
		assertEquals("/api/dow-jones-indices", res.getBody().getMessages().get(0).getFilename());
		assertEquals("succeed", res.getBody().getMessages().get(0).getStatus());
		assertEquals(1, res.getBody().getDowJonesIndices().size());
		assertEquals(15.78, res.getBody().getDowJonesIndices().get(0).getLow());
	}
}