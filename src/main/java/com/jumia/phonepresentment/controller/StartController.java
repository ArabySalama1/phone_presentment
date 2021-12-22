package com.jumia.phonepresentment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jumia.phonepresentment.dto.CustomerDTO;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StartController {

	private final CustomerService customerService;

	@RequestMapping("/")
	public String start(Model model) {
		List<CustomerDTO> customerDTOS = customerService.getCustomers(new ArrayList<>(), State.VALID);
		model.addAttribute("customersList", customerDTOS);
		return "index";
	}
}