package io.github.mariazevedo88.financialjavaapi.util;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zero_x_baadf00d.partialize.converter.Converter;

/**
 * Class that converts a given data in BigDecimal format to a JSON compatible format
 * 
 * @author Mariana Azevedo
 * @since 17/09/2020
 */
public class BigDecimalConverter implements Converter<BigDecimal> {

	/**
	 * @see com.zero_x_baadf00d.partialize.converter.Converter#convert(String, Object, ObjectNode)
	 */
	@Override
	public void convert(String fieldName, BigDecimal data, ObjectNode node) {
		node.put(fieldName, data.doubleValue());
	}

	/**
	 * @see com.zero_x_baadf00d.partialize.converter.Converter#convert(String, Object, ArrayNode)
	 */
	@Override
	public void convert(String fieldName, BigDecimal data, ArrayNode node) {
		node.add(data.doubleValue());
	}

	/**
	 * @see com.zero_x_baadf00d.partialize.converter.Converter#getManagedObjectClass()
	 */
	@Override
	public Class<BigDecimal> getManagedObjectClass() {
		return BigDecimal.class;
	}

}
