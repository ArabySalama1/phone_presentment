package com.jumia.phonepresentment.validators;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneNumberRegexValidatorTest {

	PhoneNumberRegexValidator phoneNumberRegexValidator;

	Map<String, Boolean> phoneTestExpectedMap;
	Map<String, String> phoneRegexMap;

	@BeforeEach
	public void initObj() {
		phoneNumberRegexValidator = new PhoneNumberRegexValidator();
		phoneTestExpectedMap = new HashMap<String, Boolean>() {
			{
				put("(212) 6007989253", false);
				put("(258) 847602609", true);
			}
		};

		phoneRegexMap = new HashMap<String, String>() {
			{
				put("(212) 6007989253", "\\(212\\)\\ ?[5-9]\\d{8}$");
				put("(258) 847602609", "\\(258\\)\\ ?[28]\\d{7,8}$");
			}
		};
	}

	@Test
	void shouldValidateNumberWithRegex() {
		phoneRegexMap.forEach((phone, regex) -> Assertions.assertEquals(phoneTestExpectedMap.get(phone),
				phoneNumberRegexValidator.validateNumberWithRegex(phone, regex)));
	}
}
