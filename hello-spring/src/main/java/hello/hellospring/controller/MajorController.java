package hello.hellospring.controller;

import hello.hellospring.domain.MajorDetail;
import hello.hellospring.dto.MajorDetailDTO;
import hello.hellospring.dto.MajorRequestDTO;
import hello.hellospring.service.MajorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000") //CORS ERROR 해결
public class MajorController {

    private final MajorsService majorsService;

    public MajorController(MajorsService majorsService) {
        this.majorsService = majorsService;
    }
    @ResponseBody
    @GetMapping(value = "/major", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MajorDetailDTO majorShow(@RequestBody MajorRequestDTO majorRequestDTO) {

        String major_name = majorRequestDTO.getMajor_name();
        MajorDetail majorDetail = majorsService.showAll(major_name);

        // 조회된 MajorDetail 객체를 MajorDetailDTO로 매핑
        MajorDetailDTO majorDetailDTO = new MajorDetailDTO();
        majorDetailDTO.setMajor_info(majorDetail.getMajor_info());
        majorDetailDTO.setKind_of_student(majorDetail.getKind_of_student());
        majorDetailDTO.setClass_basic(majorDetail.getClass_basic());
        majorDetailDTO.setClass_course(majorDetail.getClass_course());

        return majorDetailDTO;

    }
}