/**
 * User model class
 * This class is the model for the User object. It is used to represent a user in the system.
 * It contains the following attributes:
 * - id: the id of the user
 * - firstName: the first name of the user
 * - lastName: the last name of the user
 * - userName: the username of the user
 * - email: the email of the user
 * - phoneNumber: the phone number of the user
 * - password: the password of the user
 * - residentialAddress: the residential address of the user
 * - mailingAddress: the mailing address of the user
 * - createdAt: the date and time when the user was created
 * - updatedAt: the date and time when the user was last updated
 * - deletedAt: the date and time when the user was deleted

 * The User class is annotated with Lombok annotations for generating getters, setters, constructors, and builder methods.
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

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private Address residentialAddress;
    private Address mailingAddress;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
}




