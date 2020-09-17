package io.github.mariazevedo88.financialjavaapi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zero_x_baadf00d.partialize.converter.Converter;

/**
 * Class that converts a given data in LocalDateTime format to a JSON compatible format
 * 
 * @author Mariana Azevedo
 * @since 17/09/2020
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
	 * @see com.zero_x_baadf00d.partialize.converter.Converter#convert(String, Object, ObjectNode)
	 */
	@Override
	public void convert(String fieldName, LocalDateTime data, ObjectNode node) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
		node.put(fieldName, data.format(formatter));
	}

	/**
	 * @see com.zero_x_baadf00d.partialize.converter.Converter#convert(String, Object, ArrayNode)
	 */
	@Override
	public void convert(String fieldName, LocalDateTime data, ArrayNode node) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
		node.add(data.format(formatter));
	}

	/**
	 * @see com.zero_x_baadf00d.partialize.converter.Converter#getManagedObjectClass()
	 */
	@Override
	public Class<LocalDateTime> getManagedObjectClass() {
		return LocalDateTime.class;
	}

}
