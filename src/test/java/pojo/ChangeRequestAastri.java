package pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Setter
    @Getter
    @NoArgsConstructor
    public class ChangeRequestAastri {

        private String id;
        private String name;
        private Integer amount;

        public ChangeRequestAastri(String id,String name, Integer amount) {
            this.id = id;
            this.name = name;
            this.amount = amount;
        }
    }
