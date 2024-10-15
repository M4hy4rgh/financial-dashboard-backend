/**
 * Address model class
 * This class is the model for the Address object. It is used to represent an address in the system.
 * It contains the following attributes:
 * - id: the id of the address
 * - street: the street of the address
 * - street2: the second street of the address
 * - city: the city of the address
 * - province: the province of the address
 * - postalCode: the postal code of the address
 * - country: the country of the address
 * The class is annotated with Lombok annotations for generating getters, setters, constructors, and builder methods.
 * It is annotated with the following annotations:
 *
 * @Data - annotation to generate getters and setters for all fields.
 * @NoArgsConstructor - annotation to generate a no-argument constructor.
 * @AllArgsConstructor - annotation to generate a constructor with all arguments.
 * @Builder - annotation to generate a builder method for the class.
 * @Id - annotation to specify the field as the primary key. (Mostly for future scalability since now we are using
 * JSON Static file as data source)
 */

package ca.avengers.userservice.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    private Long id;
    private String street;
    private String street2;
    private String city;
    private String province;
    private String postalCode;
    private String country;
}
