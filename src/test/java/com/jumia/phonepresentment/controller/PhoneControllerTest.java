package com.jumia.phonepresentment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jumia.phonepresentment.dto.CustomerDTO;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.model.Customer;
import com.jumia.phonepresentment.repostitory.CustomerRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customerRepository;
	private List<Customer> validCustomers;
	private List<Customer> invalidCustomers;

	@BeforeEach
	private void init() {
		validCustomers = new ArrayList<Customer>() {
			private static final long serialVersionUID = 1L;

			{
				add(Customer.builder().name("C1").phone("(237) 697151594").build());
				add(Customer.builder().name("C2").phone("(237) 677046616").build());
				add(Customer.builder().name("C3").phone("(237) 673122155").build());
			}
		};

		invalidCustomers = new ArrayList<Customer>() {
			private static final long serialVersionUID = 1L;

			{
				add(Customer.builder().name("C4").phone("(237) 6A0311634").build());
				add(Customer.builder().name("C5").phone("(237) 6A0311635").build());
				add(Customer.builder().name("C6").phone("(237) 6A0311636").build());
			}
		};
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void whenGetValidCustomerNumberThenCorrectResponse() throws Exception {
		// Given
		String[] countries = new String[1];
		countries[0] = "Cameroon";
		List<CustomerDTO> customerDTOs = new ArrayList<>();
		CustomerDTO customerDTO = new CustomerDTO("C1", "(237) 697151594", "Cameroon", State.VALID);
		CustomerDTO customerDTO2 = new CustomerDTO("C2", "(237) 677046616", "Cameroon", State.VALID);
		CustomerDTO customerDTO3 = new CustomerDTO("C3", "(237) 673122155", "Cameroon", State.VALID);

		customerDTOs.add(customerDTO);
		customerDTOs.add(customerDTO2);
		customerDTOs.add(customerDTO3);
		when(customerRepository.findAll(Mockito.any(Specification.class))).thenReturn(validCustomers);

		// When
		ResultActions result = mockMvc
				.perform(get("/api/v1/phone-presentment/present").param("countries", countries).param("state", "VALID"))
				.andDo(print());

		// Then
		result.andExpect(MockMvcResultMatchers.status().isOk());

		for (int i = 0; i < customerDTOs.size(); i++) {
			CustomerDTO cs = customerDTOs.get(i);
			result.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].name", Is.is(cs.getName())))
					.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].phone", Is.is(cs.getPhone())))
					.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].country", Is.is(cs.getCountry())))
					.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].phoneNumberState",
							Is.is(cs.getPhoneNumberState().toString())));
		}

	}

	@Test
	public void whenGetInValidCustomerNumberThenCorrectResponse() throws Exception {
		// Given
		String[] countries = new String[1];
		countries[0] = "Cameroon";
		List<CustomerDTO> customerDTOs = new ArrayList<>();
		CustomerDTO customerDTO = new CustomerDTO("C4", "(237) 6A0311634", "Cameroon", State.INVALID);
		CustomerDTO customerDTO2 = new CustomerDTO("C5", "(237) 6A0311635", "Cameroon", State.INVALID);
		CustomerDTO customerDTO3 = new CustomerDTO("C6", "(237) 6A0311636", "Cameroon", State.INVALID);

		customerDTOs.add(customerDTO);
		customerDTOs.add(customerDTO2);
		customerDTOs.add(customerDTO3);
		when(customerRepository.findAll(Mockito.any(Specification.class))).thenReturn(invalidCustomers);

		// When
		ResultActions result = mockMvc.perform(
				get("/api/v1/phone-presentment/present").param("countries", countries).param("state", "INVALID"))
				.andDo(print());

		// Then
		result.andExpect(MockMvcResultMatchers.status().isOk());

		for (int i = 0; i < customerDTOs.size(); i++) {
			CustomerDTO cs = customerDTOs.get(i);
			result.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].name", Is.is(cs.getName())))
					.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].phone", Is.is(cs.getPhone())))
					.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].country", Is.is(cs.getCountry())))
					.andExpect(MockMvcResultMatchers.jsonPath("$[" + i + "].phoneNumberState",
							Is.is(cs.getPhoneNumberState().toString())));
		}

	}

}
