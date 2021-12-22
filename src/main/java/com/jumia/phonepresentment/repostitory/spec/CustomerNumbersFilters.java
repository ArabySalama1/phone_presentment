package com.jumia.phonepresentment.repostitory.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.jumia.phonepresentment.dto.CustomerDTO;
import com.jumia.phonepresentment.enums.State;
import com.jumia.phonepresentment.model.Customer;

public class CustomerNumbersFilters {

	private CustomerNumbersFilters() {
	}

	public static Specification<Customer> countriesFilter(List<String> customerPhones) {

		return (root, criteriaQuery, criteriaBuilder) -> {
			if (CollectionUtils.isEmpty(customerPhones)) {
				return criteriaBuilder.conjunction();
			} else {

				List<Predicate> predicates = new ArrayList<>();
				customerPhones.forEach(customerPhone -> predicates.add(criteriaBuilder
						.like(criteriaBuilder.lower(root.get("phone")), "(" + customerPhone.toLowerCase() + "%")));
				return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
			}
		};
	}

	public static java.util.function.Predicate<CustomerDTO> isStateValid(State phoneNumberState) {
		return customerDTO -> phoneNumberState == null || customerDTO.getPhoneNumberState().equals(phoneNumberState);
	}
}
