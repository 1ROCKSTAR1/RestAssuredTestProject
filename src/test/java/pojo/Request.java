package pojo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Request {

    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private String userStatus;

}
