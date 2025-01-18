package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuccessRegResponse {

    private Integer id;
    private String token;

    public SuccessRegResponse(Integer id, String token) {
        this.id = id;
        this.token = token;
    }
}
