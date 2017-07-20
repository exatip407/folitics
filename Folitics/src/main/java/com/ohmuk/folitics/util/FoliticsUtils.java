package com.ohmuk.folitics.util;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class FoliticsUtils {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(FoliticsUtils.class);

	public static final String getUserName() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth == null)
			return null;
		return auth.getName();
	}

	public static double getRoundedDouble(double value) {

		DecimalFormat f = new DecimalFormat("##.00");
		return Double.parseDouble(f.format(value));
	}
}
