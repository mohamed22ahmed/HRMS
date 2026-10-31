package com.hrms.employee_service.dto.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ApiResponseDto {
    private ApiResponseDto() {}

    public static Map<String, Object> success(int status, String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static <T> Map<String, Object> successList(int status, String message, List<T> items, Function<T, Object> mapper) {
        return success(status, message, items.stream().map(mapper).toList());
    }

    public static Map<String, Object> failure(int status, String message, Map<String, String> errors) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        if (errors != null && !errors.isEmpty()) {
            response.put("errors", errors);
        }
        return response;
    }
}
