package ufps.edu.co.wompi.model;

import lombok.Builder;

@Builder
public record WompiCustomerData(
        String email,
        String fullName,
        String phoneNumber,
        String phoneNumberPrefix,
        String legalId,
        String legalIdType) {
}