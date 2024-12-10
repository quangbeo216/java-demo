package com.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiController {

    protected ResponseEntity<Map<String, Object>>  result(int code, boolean error, String message, Map<String,Object> response) {
        Map<String, Object> result = new HashMap<>();
        result.put("code",code);
        result.put("error", error);
        result.put("message", message);
        result.put("data", response);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> success(String message, Map<String,Object> response) {
        return result(200,false,message,response);
    }

    public ResponseEntity<Map<String, Object>> error(String message, Map<String,Object> response) {
        return result(500,true,message,response);
    }
}
