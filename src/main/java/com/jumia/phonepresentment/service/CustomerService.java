package com.jumia.phonepresentment.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jumia.phonepresentment.dto.CustomerDTO;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.model.Customer;
import com.jumia.phonepresentment.repostitory.CustomerRepository;
import com.jumia.phonepresentment.repostitory.spec.CustomerNumbersFilters;
import com.jumia.phonepresentment.service.helper.CountryService;
import com.jumia.phonepresentment.service.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	private final CountryService countryService;

	public List<CustomerDTO> getCustomers(List<String> countries, State state) {
		List<Customer> customers = customerRepository.findAll(getCountriesFilterSpecification(countries));
		return customerMapper.customersToCustomerDtos(customers).stream()
				.filter(CustomerNumbersFilters.isStateValid(state)).collect(Collectors.toList());
	}

	private Specification<Customer> getCountriesFilterSpecification(List<String> countries) {
		return CustomerNumbersFilters.countriesFilter(Optional.ofNullable(countries).map(Collection::stream)
				.orElseGet(Stream::empty).map(countryService::getCodeByCountry).collect(Collectors.toList()));
	}

}
