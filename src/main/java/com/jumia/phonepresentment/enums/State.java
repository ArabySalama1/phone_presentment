package com.jumia.phonepresentment.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum State {

	VALID(0), INVALID(1);

	private final Integer value;
}
