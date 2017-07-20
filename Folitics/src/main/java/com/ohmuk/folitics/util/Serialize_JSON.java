/**
 * 
 */
package com.ohmuk.folitics.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Serialize_JSON {
	private static final transient Logger LOG = LoggerFactory.getLogger(Serialize_JSON.class);
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public static byte[] getBytes(Object objectToSerialize) throws Exception {
		return MAPPER.writeValueAsBytes(objectToSerialize);
	}
	
	public static String getJSONString(Object objectToSerialize) throws Exception {
		return MAPPER.writeValueAsString(objectToSerialize);	
	}
	
	public static String getJSONStringForLogging(Object objectToSerialize) 
	{
		String jsonString = null;
		try
		{
			jsonString = MAPPER.writeValueAsString(objectToSerialize);
		}
		catch(Exception ex)
		{

		}
		return jsonString; 	
	}
	
	public static String getPrettyPrintJSONStringForLogging(Object objectToSerialize) 
	{
		String jsonString = null;
		try
		{
			jsonString = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(objectToSerialize);
		}
		catch(Exception ex)
		{
			LOG.error("Error while pretty printing JSON String. Exception:'{}'", ex.toString());
		}
		return jsonString; 	
	}
	
	public static <T extends Object> T getObject(String stringToDeSerialize, Class<T> schema) throws Exception {
		return (T) MAPPER.readValue(stringToDeSerialize, schema);
	}
	
	public static <T extends Object> T getObject(byte[] bytesToDeSerialize, Class<T> schema) throws Exception {
		return (T) MAPPER.readValue(bytesToDeSerialize, schema);
	}
	
	public static <T extends Object> T getObject(File file, Class<T> schema) throws Exception {
		return (T) MAPPER.readValue(file, schema);
	}
	
	public static <T extends Object> T getObject(String stringToDeSerialize, @SuppressWarnings("rawtypes") TypeReference schema) throws Exception {
		return MAPPER.readValue(stringToDeSerialize, schema);
	}
	
}
