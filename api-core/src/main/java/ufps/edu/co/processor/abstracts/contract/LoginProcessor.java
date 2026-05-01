package ufps.edu.co.processor.abstracts.contract;

import javax.security.sasl.AuthenticationException;

import ufps.edu.co.records.input.usecase.LoginInput;
import ufps.edu.co.records.output.usecase.LoginOutput;

public interface LoginProcessor {
    LoginOutput authenticate(LoginInput input) throws AuthenticationException;
    LoginOutput refreshToken(String token) throws AuthenticationException;
}
