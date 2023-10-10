package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuideDTO {
    private String major;
    private SubjectDataDTO subjectData;

    public String GuideDTO(String major, SubjectDataDTO subjectData){
        this.major = major;
        this.subjectData = subjectData;
    }
}
