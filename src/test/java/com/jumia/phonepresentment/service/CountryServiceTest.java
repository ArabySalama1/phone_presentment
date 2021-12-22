package com.jumia.phonepresentment.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jumia.phonepresentment.enums.CountryEnum;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.service.helper.CountryService;
import com.jumia.phonepresentment.validators.PhoneNumberRegexValidator;

@SpringBootTest
class CountryServiceTest {

	@Autowired
	private CountryService countryService;

	@MockBean
	private PhoneNumberRegexValidator phoneNumberRegexValidator;

	@BeforeEach
	void initObj() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	void shouldReturnInvalidForInputCountryPhone() {
		State phoneNumberState = countryService.isCountryPhoneValid("213422");
		Assertions.assertEquals(State.INVALID, phoneNumberState);
	}

	@Test
	void shouldReturnValidForInputCountryPhone() {
		when(phoneNumberRegexValidator.validateNumberWithRegex(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		State phoneNumberState = countryService.isCountryPhoneValid("(212) 698054317");
		Assertions.assertEquals(State.VALID, phoneNumberState);
	}

	@Test
	void shouldReturnMorroccoForInputCountryPhone() {
		String country = countryService.getCountryByCode("(212) 698054317");
		Assertions.assertEquals(CountryEnum.MOROCCO.getCountryName(), country);
	}

	@Test
	void shouldReturnEmptyForInputCountryPhone() {
		String country = countryService.getCountryByCode("(235) 698054317");
		Assertions.assertEquals("", country);
	}

	@Test
	void shouldReturnValidCountryCodeForInputCountryMorocco() {
		String code = countryService.getCodeByCountry(CountryEnum.MOROCCO.getCountryName());
		Assertions.assertEquals(CountryEnum.MOROCCO.getCode(), code);
	}

	@Test
	void shouldReturnEmptyCountryCodeForInputCountryMorocco() {
		String code = countryService.getCodeByCountry("Italy");
		Assertions.assertEquals("", code);
	}
}
