package pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static utils.RandomEmail.generateRandomEmail;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestForPostman {

    private String firstName;
    private String lastName;
    private String email;

    public RequestForPostman(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = generateRandomEmail();
    }

}
