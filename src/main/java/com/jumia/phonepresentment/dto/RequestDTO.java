package com.jumia.phonepresentment.dto;

import java.util.List;

import com.jumia.phonepresentment.enums.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

	private List<String> countries;
	private State state;
}
