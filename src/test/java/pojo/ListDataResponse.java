package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListDataResponse {

    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;

    public ListDataResponse(Integer id, String pantone_value, String color, Integer year, String name) {
        this.id = id;
        this.pantone_value = pantone_value;
        this.color = color;
        this.year = year;
        this.name = name;
    }
}
