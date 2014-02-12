package com.xebia.interview.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility class for common method.
 * 
 * @author Zied ANDOLSI
 * 
 */
public final class CommonUtil {

	/**
	 * Prevents instantiation.
	 */
	private CommonUtil() {

	}

	/**
	 * This method allows to split a string and ignores the space between the double quotes.
	 * 
	 * @param line
	 *            string to split.
	 * @return tokens.
	 */
	public static String[] split(String line) {
		List<String> parts = new ArrayList<String>();
		if (line != null && line.trim().length() != 0) {
			boolean escaped = false;
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (!escaped && (c == ' ' || c == '\t')) {
					parts.add(stringBuilder.toString());
					stringBuilder = new StringBuilder();
				} else {
					if (c == '"') {
						escaped = !escaped;
					} else {
						stringBuilder.append(c);
					}
				}
			}
			parts.add(stringBuilder.toString());
		}
		return parts.toArray(new String[parts.size()]);
	}

	/**
	 * Testing if a common used object is null or empty.
	 * 
	 * @param object
	 *            object to validate
	 * @return Boolean value indicates if any object is null and if a string, a map or a collection is empty
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isNullOrEmpty(Object object) {
		if (object == null) {
			return Boolean.TRUE;
		}
		if (object instanceof String) {
			return ((String) object).trim().length() == 0;
		}
		if (object instanceof Map) {
			return ((Map) object).isEmpty();
		}
		if (object instanceof Collection) {
			return ((Collection) object).isEmpty();
		}
		return Boolean.FALSE;
	}
}
