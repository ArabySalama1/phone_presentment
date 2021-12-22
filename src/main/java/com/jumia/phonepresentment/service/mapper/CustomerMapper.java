package com.jumia.phonepresentment.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumia.phonepresentment.dto.CustomerDTO;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.model.Customer;
import com.jumia.phonepresentment.service.helper.CountryService;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class CustomerMapper {

	@Autowired
	CountryService countryService;

	public abstract List<CustomerDTO> customersToCustomerDtos(List<Customer> customer);

	@Mapping(target = "country", source = "phone", qualifiedByName = "getCountryByPhone")
	@Mapping(target = "phoneNumberState", source = "phone", qualifiedByName = "getStateByPhone")
	public abstract CustomerDTO customerToCustomerDto(Customer customer);

	@Named("getCountryByPhone")
	public String getCountryByPhone(String phone) {
		return countryService.getCountryByCode(phone);
	}

	@Named("getStateByPhone")
	public State getStateByPhone(String phone) {
		return countryService.isCountryPhoneValid(phone);
	}

}
