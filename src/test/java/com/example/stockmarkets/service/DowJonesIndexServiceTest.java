package com.example.stockmarkets.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.stockmarkets.document.DowJonesIndex;
import com.example.stockmarkets.repository.DowJonesIndexRepository;
import com.example.stockmarkets.util.JsonLoaderUtil;

@ExtendWith(MockitoExtension.class)
public class DowJonesIndexServiceTest {
	@Mock
	private DowJonesIndexRepository dowJonesIndexRepository;

	@InjectMocks
	private DowJonesIndexService dowJonesIndexService;

	@BeforeEach
	public void setUp() {
	}

	@Test
	public void testDelete_withNoExistingDocument() {
		when(dowJonesIndexRepository.findById(anyString())).thenReturn(Optional.empty());
		assertNull(this.dowJonesIndexService.deleteDowJonesIndex("12344"));

		verify(dowJonesIndexRepository, times(1)).findById(anyString());
		verify(dowJonesIndexRepository, never()).deleteById(anyString());
	}

	@Test
	public void testDelete_withExistingDocument() {
		DowJonesIndex mockDowJonesIndex = JsonLoaderUtil.getObjectFrom(
				"dow-jones-index.json",
				DowJonesIndex.class);
		when(dowJonesIndexRepository.findById(anyString()))
				.thenReturn(Optional.of(mockDowJonesIndex));
		// doNothing().when(dowJonesIndexRepository).deleteById(anyString());

		DowJonesIndex deletedObject = this.dowJonesIndexService.deleteDowJonesIndex("12344");
		assertEquals(deletedObject, mockDowJonesIndex);

		verify(dowJonesIndexRepository, times(1)).findById(anyString());
		verify(dowJonesIndexRepository, times(1)).deleteById(anyString());
	}

}
