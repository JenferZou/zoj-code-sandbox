package com.jenfer.sandbox;


import com.jenfer.sandbox.model.ExecuteCodeRequest;
import com.jenfer.sandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Service;

@Service
public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequestion);


}
