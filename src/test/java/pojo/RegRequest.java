package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegRequest {

    private String email;
    private String password;

    public RegRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
