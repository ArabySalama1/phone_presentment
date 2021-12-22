package com.jumia.phonepresentment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {

	private String countryName;
	private String countryCode;
	private String regex;
}
