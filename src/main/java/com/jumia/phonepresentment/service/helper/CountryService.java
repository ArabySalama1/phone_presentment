package com.jumia.phonepresentment.service.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jumia.phonepresentment.dto.CountryDTO;
import com.jumia.phonepresentment.enums.CountryEnum;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.validators.PhoneNumberRegexValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {

	private final PhoneNumberRegexValidator phoneNumberRegexValidator;
	private List<CountryDTO> countryDTOs;
	private Map<String, CountryDTO> codeCountryMap;
	private Map<String, CountryDTO> nameCountryMap;

	@PostConstruct
	private void init() {
		List<CountryDTO> countryDTOS = new ArrayList<>();

		countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.CAMEROON.getCountryName())
				.countryCode(CountryEnum.CAMEROON.getCode()).regex(CountryEnum.CAMEROON.getRegex()).build());

		countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.ETHIOPIA.getCountryName())
				.countryCode(CountryEnum.ETHIOPIA.getCode()).regex(CountryEnum.ETHIOPIA.getRegex()).build());

		countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.MOROCCO.getCountryName())
				.countryCode(CountryEnum.MOROCCO.getCode()).regex(CountryEnum.MOROCCO.getRegex()).build());

		countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.MOZAMBIQUE.getCountryName())
				.countryCode(CountryEnum.MOZAMBIQUE.getCode()).regex(CountryEnum.MOZAMBIQUE.getRegex()).build());

		countryDTOS.add(CountryDTO.builder().countryName(CountryEnum.UGANDA.getCountryName())
				.countryCode(CountryEnum.UGANDA.getCode()).regex(CountryEnum.UGANDA.getRegex()).build());

		this.countryDTOs = countryDTOS;

		this.codeCountryMap = countryDTOs.stream()
				.collect(Collectors.toMap(CountryDTO::getCountryCode, countryDTO -> countryDTO));

		this.nameCountryMap = countryDTOs.stream()
				.collect(Collectors.toMap(CountryDTO::getCountryName, countryDTO -> countryDTO));

	}

	public State isCountryPhoneValid(String phoneNumber) {

		List<Function<String, Boolean>> validations = Arrays.asList(this::isPhoneNumberOrPhoneCodeValid,
				this::isPhoneRegexValid);

		for (Function<String, Boolean> validation : validations) {
			if (Boolean.FALSE.equals(validation.apply(phoneNumber))) {
				return State.INVALID;
			}
		}

		return State.VALID;
	}

	private boolean isPhoneRegexValid(String phoneNumber) {

		return phoneNumberRegexValidator.validateNumberWithRegex(phoneNumber,
				codeCountryMap.get(extractCountryCodeFromPhone(phoneNumber)).getRegex());
	}

	private String extractCountryCodeFromPhone(String phoneNumber) {
		return StringUtils.substringBetween(phoneNumber, "(", ")");
	}

	private boolean isPhoneNumberOrPhoneCodeValid(String phoneNumber) {
		return StringUtils.isNoneBlank(phoneNumber)
				&& StringUtils.isNoneBlank(extractCountryCodeFromPhone(phoneNumber));
	}

	public String getCountryByCode(String phoneNumber) {
		return isPhoneNumberOrPhoneCodeValid(phoneNumber) ? getCountryName(phoneNumber, codeCountryMap) : "";
	}

	private String getCountryName(String phoneNumber, Map<String, CountryDTO> countryDTOSMap) {
		String code = extractCountryCodeFromPhone(phoneNumber);
		return countryDTOSMap.get(code) == null ? "" : countryDTOSMap.get(code).getCountryName();
	}

	public String getCodeByCountry(String country) {

		return StringUtils.isBlank(country) ? "" : getCountryCode(country, nameCountryMap);
	}

	private String getCountryCode(String country, Map<String, CountryDTO> countryDTOSMap) {
		return (countryDTOSMap.get(country) == null) ? "" : countryDTOSMap.get(country).getCountryCode();
	}

}
