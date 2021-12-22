package com.jumia.phonepresentment.validators;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class PhoneNumberRegexValidator {

	public boolean validateNumberWithRegex(String phoneNumber, String regex) {
		return Pattern.matches(regex, phoneNumber);
	}
}
