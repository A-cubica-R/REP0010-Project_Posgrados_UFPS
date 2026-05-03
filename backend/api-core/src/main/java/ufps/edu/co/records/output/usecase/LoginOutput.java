package ufps.edu.co.records.output.usecase;

import java.util.List;

import ufps.edu.co.records.OutputResponse;

public record LoginOutput(
        String token,
        String refreshToken,
        Integer userId,
        String userName,
        List<String> roles)
        implements OutputResponse {
}
