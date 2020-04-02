package com.softserve.edu.rest.dto;

import java.util.HashMap;
import java.util.Map;

public class RestParameters {
	private Map<EParameters, String> parameters;

    public RestParameters() {
        parameters = new HashMap<>();
    }

    public RestParameters addParameter(EParameters key, String value) {
        parameters.put(key, value);
        return this;
    }

    public String getParameter(EParameters key) {
        return parameters.get(key);
    }

    public Map<EParameters, String> getAllParameters() {
        return parameters;
    }
}
