package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDataResponse {

    private Integer id;
    private String name;
    private Integer amount;

    public ProductDataResponse(Integer id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
