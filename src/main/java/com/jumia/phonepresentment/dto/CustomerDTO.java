package com.jumia.phonepresentment.dto;

import com.jumia.phonepresentment.enums.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerDTO {

	private String name;
	private String phone;
	private String country;
	private State phoneNumberState;
}
