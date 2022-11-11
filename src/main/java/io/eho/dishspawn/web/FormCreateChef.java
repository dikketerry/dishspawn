package io.eho.dishspawn.web;

import io.eho.dishspawn.model.Chef;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class FormCreateChef {

    @Size(max = 27, message = "First name must be less than 28 symbols")
    private String firstName;

    @Size(max = 27, message = "Last name must be less than 28 symbols")
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 27, message = "Username must be more than 1 and less than 28 symbols")
    private String userName;

    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])"    // must contain min 1 number
            + "(?=.*[A-z])"             // must contain min 1 letter
            + "(?=\\S+$).{6,20}$",      // must be between 6-20 chars and cannot contain spaces
            message = "password must be between 6 - 20 characters long, cannot contain spaces and should contain at " +
                    "least 1 letter and 1 number")
    private String password;

    private String retypePassword;

    private boolean passwordsEqual;

    private String avatarPath;

    public Chef toChef() {
        return new Chef(firstName, lastName, userName, email, password, avatarPath);
    }

    @AssertTrue(message= "Passwords should match")
    public boolean isPasswordsEqual() {
        return (password == null) ? false : password.equals(retypePassword);
    }

    @Override
    public String toString() {
        return "FormCreateChef{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", userName='" + userName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", retypePassword='" + retypePassword + '\'' + ", avatarPath='" + avatarPath + '\'' + '}';
    }
}
