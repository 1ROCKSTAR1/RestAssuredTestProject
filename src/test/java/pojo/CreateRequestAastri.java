package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateRequestAastri {

    private String name;
    private Integer amount;

    public CreateRequestAastri(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }
}
