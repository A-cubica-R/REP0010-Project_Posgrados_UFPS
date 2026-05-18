package ufps.edu.co.auth.processor;

import org.springframework.stereotype.Service;

import ufps.edu.co.auth.contract.AuthenticationUseCase;
import ufps.edu.co.auth.contract.TokenIssuer;
import ufps.edu.co.auth.exception.InvalidCredentialsException;
import ufps.edu.co.auth.exception.MissingCredentialsException;
import ufps.edu.co.auth.exception.RoleMismatchException;
import ufps.edu.co.auth.model.AuthPrincipal;
import ufps.edu.co.auth.records.input.LoginInput;
import ufps.edu.co.auth.records.output.LoginOutput;
import ufps.edu.co.auth.service.CredentialService;
import ufps.edu.co.auth.service.RoleNameNormalizer;

@Service
public class AuthenticationProcessor implements AuthenticationUseCase {

    private final CredentialService credentialService;
    private final TokenIssuer tokenIssuer;

    public AuthenticationProcessor(CredentialService credentialService, TokenIssuer tokenIssuer) {
        this.credentialService = credentialService;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public LoginOutput login(LoginInput input) {
        return login(input, null);
    }

    @Override
    public LoginOutput login(LoginInput input, String requestedRole) {
        if (input == null) {
            throw new MissingCredentialsException();
        }
        if (input.username() == null || input.username().isBlank() || input.password() == null || input.password().isBlank()) {
            throw new MissingCredentialsException();
        }

        AuthPrincipal principal = credentialService.authenticate(input.username(), input.password());
        if (principal == null) {
            throw new InvalidCredentialsException();
        }

        if (requestedRole != null && !requestedRole.isBlank()) {
            validateUserRoleMatchesRequested(principal, requestedRole);
        }

        return tokenIssuer.issueTokens(principal);
    }

    private void validateUserRoleMatchesRequested(AuthPrincipal principal, String requestedRole) {
        String normalizedRequestedRole = RoleNameNormalizer.normalize(requestedRole);
        boolean hasRole = principal.roles().stream()
                .anyMatch(role -> RoleNameNormalizer.normalize(role).equals(normalizedRequestedRole));

        if (!hasRole) {
            throw new RoleMismatchException(
                    "User does not have the requested role: " + requestedRole);
        }
    }
}