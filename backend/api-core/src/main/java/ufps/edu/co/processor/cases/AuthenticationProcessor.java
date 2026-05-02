package ufps.edu.co.processor.cases;

import javax.security.sasl.AuthenticationException;

import ufps.edu.co.processor.abstracts.contract.LoginProcessor;
import ufps.edu.co.records.input.usecase.LoginInput;
import ufps.edu.co.records.output.usecase.LoginOutput;

public class AuthenticationProcessor implements LoginProcessor {

    @Override
    public LoginOutput authenticate(LoginInput input) throws AuthenticationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }

    @Override
    public LoginOutput refreshToken(String token) throws AuthenticationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshToken'");
    }
    
}
