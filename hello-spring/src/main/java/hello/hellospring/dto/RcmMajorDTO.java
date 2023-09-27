package hello.hellospring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class RcmMajorDTO {

    @Getter
    @Setter
    private String rcmMajor1;

    @Getter
    @Setter
    private String rcmMajor2;

    @Getter
    @Setter
    private String rcmMajor3;

    @Builder
    public RcmMajorDTO(String rcmMajor1, String rcmMajor2, String rcmMajor3){
        this.rcmMajor1=rcmMajor1;
        this.rcmMajor2=rcmMajor2;
        this.rcmMajor3=rcmMajor3;
    }

}
