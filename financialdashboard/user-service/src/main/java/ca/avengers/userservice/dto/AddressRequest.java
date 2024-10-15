package ca.avengers.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private String street;

    private String street2;

    private String city;

    private String province;

    private String postalCode;

    private String country;
}
