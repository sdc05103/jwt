package hello.hellospring.controller;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.RcmMajor;
import hello.hellospring.domain.Subject;
import hello.hellospring.dto.CreditEditDTO;
import hello.hellospring.dto.HopeMajorDTO;
import hello.hellospring.dto.RcmMajorDTO;
import hello.hellospring.repository.RcmMajorRepository;
import hello.hellospring.service.RcmMajorService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") //CORS ERROR 해결
@RequiredArgsConstructor
@RestController
public class RcmMajorController {

    private final RcmMajorService rcmMajorService;

    @ResponseBody
    @RequestMapping (value = "api/chatbot1")
    public ResponseEntity<String> HopeMajorEdit(@RequestBody HopeMajorDTO hope_major, @RequestHeader("Authorization") String token) {

        String id = Jwts.parserBuilder()
                .setSigningKey("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK" .getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();

        ///이전에 저장돼있던 추천학과 삭제하는 함수 추가////////

        String major = hope_major.getHopeMajor();

        int hope_major_id = rcmMajorService.findMajorId(major);

        RcmMajor rcmMajor = new RcmMajor();
        rcmMajor.setSid(id);
        rcmMajor.setMid1(hope_major_id);
        rcmMajor.setMid2(hope_major_id);
        rcmMajor.setMid3(hope_major_id);

        //DB에 rcmMajor object 등록하는 함수
        if(hope_major_id != 0) {
            rcmMajorService.RcmMajorEdit(rcmMajor);
        }

        return ResponseEntity.ok("Successfully processed major data");
    }

    @ResponseBody
    @RequestMapping (value = "api/chatbot2")
    public ResponseEntity<String> RecommendMajorEdit(@RequestBody RcmMajorDTO rcmMajorDTO, @RequestHeader("Authorization") String token) {

        String id = Jwts.parserBuilder()
                .setSigningKey("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK" .getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();

        String major1 = rcmMajorDTO.getRcmMajor1();
        String major2 = rcmMajorDTO.getRcmMajor2();
        String major3 = rcmMajorDTO.getRcmMajor3();

        int rcm_major_id1 = rcmMajorService.findMajorId(major1);
        int rcm_major_id2 = rcmMajorService.findMajorId(major2);
        int rcm_major_id3 = rcmMajorService.findMajorId(major3);

        System.out.println(rcm_major_id2);

        return ResponseEntity.ok("Successfully processed major data");
    }


    }

