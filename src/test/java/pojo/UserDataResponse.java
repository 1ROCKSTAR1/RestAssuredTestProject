package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDataResponse {

    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    public UserDataResponse(Integer id, String email, String first_name, String last_name, String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }


}
