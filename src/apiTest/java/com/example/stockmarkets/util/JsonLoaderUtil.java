package com.example.stockmarkets.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class JsonLoaderUtil {
	private static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	private static ObjectMapper jacksonObjectMapper = new ObjectMapper();

	static {
		config(jacksonObjectMapper);
	}

	/**
	 * config objectMapper.
	 * 
	 * @param objectMapper instance of objectMapper
	 */
	public static void config(ObjectMapper objectMapper) {
		new Jackson2ObjectMapperBuilder()
				.indentOutput(true)
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.simpleDateFormat(ISO_DATE_TIME_FORMAT)
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.timeZone(TimeZone.getTimeZone("UTC"))
				.deserializersByType(
						ImmutableMap.of(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
							@Override
							public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt)
									throws IOException, JsonProcessingException {
								String text = jsonParser.getText();
								return OffsetDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
							}
						}))
				.configure(objectMapper);
	}

	public static InputStream getJsonAsStream(String jsonFileName) {
		return JsonLoaderUtil.class.getResourceAsStream("/json/" + jsonFileName);
	}

	/**
	 * deserialize from json file.
	 * 
	 * @param jsonFileName  name of json file
	 * @param typeReference type reference of the object
	 * @param <T>           type of the object
	 * @return target object
	 */
	public static <T> T getObjectFrom(String jsonFileName, TypeReference<T> typeReference) {
		try {
			InputStream in = getJsonAsStream(jsonFileName);
			return jacksonObjectMapper.readValue(in, typeReference);
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * deserialize from json file.
	 * 
	 * @param jsonFileName name of json file
	 * @param clazz        class type of the object
	 * @param <T>          type of the object
	 * @return target object
	 */
	public static <T> T getObjectFrom(String jsonFileName, Class<T> clazz) {
		try {
			InputStream in = JsonLoaderUtil.class.getResourceAsStream("/json/" + jsonFileName);
			return jacksonObjectMapper.readValue(in, clazz);
		} catch (IOException ex) {
			return null;
		}
	}
}