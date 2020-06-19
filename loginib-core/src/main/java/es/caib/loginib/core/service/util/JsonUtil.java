package es.caib.loginib.core.service.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.loginib.core.api.exception.JsonException;

public final class JsonUtil {

	private JsonUtil() {
		super();
	}

	public static Object fromJson(final String json, final Class clase) throws JsonException {
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			final Object obj = objectMapper.readValue(json, clase);
			return obj;
		} catch (final IOException e) {
			throw new JsonException(e);
		}
	}

	public static String toJson(final Object o) throws JsonException {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			return objectMapper.writeValueAsString(o);
		} catch (final JsonProcessingException e) {
			throw new JsonException(e);
		}
	}
}
