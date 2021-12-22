package com.jumia.phonepresentment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumia.phonepresentment.dto.CustomerDTO;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phone-presentment")
public class PhoneController {

	private final CustomerService customerService;

	@ApiOperation(value = "This App used to present customers numbers with state and country")
	@GetMapping("/present")
	public ResponseEntity<List<CustomerDTO>> getAllNumbers(@RequestParam(name = "countries") List<String> countries,
			@RequestParam(name = "state") State state) {
		return ResponseEntity.ok(customerService.getCustomers(countries, state));
	}

}
