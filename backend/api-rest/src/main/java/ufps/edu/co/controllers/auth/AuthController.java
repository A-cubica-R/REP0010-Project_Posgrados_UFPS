package ufps.edu.co.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ufps.edu.co.auth.contract.AuthenticationUseCase;
import ufps.edu.co.auth.contract.RefreshTokenUseCase;
import ufps.edu.co.auth.exception.InvalidCredentialsException;
import ufps.edu.co.auth.exception.InvalidTokenException;
import ufps.edu.co.auth.exception.MissingCredentialsException;
import ufps.edu.co.auth.records.input.LoginInput;
import ufps.edu.co.auth.records.input.RefreshTokenInput;
import ufps.edu.co.auth.records.output.LoginOutput;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private AuthenticationUseCase authenticationUseCase;

    @Autowired
    private RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginOutput> login(@RequestBody @Valid LoginInput input) {
        try {
            return ResponseEntity.ok(authenticationUseCase.login(input));
        } catch (MissingCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (InvalidCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginOutput> refresh(@RequestBody @Valid RefreshTokenInput input) {
        try {
            return ResponseEntity.ok(refreshTokenUseCase.refresh(input.refreshToken()));
        } catch (InvalidTokenException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (MissingCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}