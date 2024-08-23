package com.jenfer.sandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExecuteCodeRequest {

    private List<String> inputList;

    private String code;

    private String language;


}
