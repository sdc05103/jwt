package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompleteDTO {
    String class_name;
    String credit;
    String subject_name;

    public CompleteDTO() {
    }

    public CompleteDTO(String class_name, String credit, String subject_name) {
        this.class_name = class_name;
        this.credit = credit;
        this.subject_name = subject_name;
    }
}
