package hello.hellospring.controller;

import hello.hellospring.dto.AllClassDTO;
import hello.hellospring.dto.GuideDTO;
import hello.hellospring.service.GuideService;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @GetMapping(value = "/api/guide")
    public List<GuideDTO> creditShow(@RequestHeader("Authorization") String token) {

        String id = Jwts.parserBuilder()
                .setSigningKey("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK" .getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();

        System.out.println(id);

        //로그인 된 유저의 추천 major 받아오는 코드
        List<String> major_list = guideService.showAll(id);


        //채윤
        //major가 1개인지 3개인지 검사(list 요소의 개수 확인하는 함수)
        int major_num = major_list.size();

        //major 개수만큼 실행하는 반복문(1 or 3)
        for(int i=0 ; i<major_num; i++) {

            String major = major_list.get(i);
            //IF : 유저의 가이드 데이터가 디비에 있는 경우 그대로 리턴 //total_guide 테이블에 현재 사용자 id 있는지 check
            if(id == guideService.getSID(id)) { //로그인한 사용자의 id와 total_guide의 sid가 같다면
                List<GuideDTO> GuideList = guideService.getAllTotalguide(id);
                return GuideList;
            }

            //ELSE : 그렇지 않은 경우 생성
            //여기까지 채윤
            else {

                //진현 - 다 했어요.
                //모든 과목 가져오기 - (class, subject 조인)
                List<AllClassDTO> GuideList = guideService.getAllClass(major);

                //이미 들은 과목 가져오기 - (class_list, class, subject 조인)
                //해당 전공에 추천하는 과목 다 받아오기 (major_list에서 받아온 다음, 파싱 작업 후 class, subject 조인해서 각 과목에 해당하는 학점, 계열 가져오기)
                //모든 과목 가져오기 - (class, subject 조인)

                //모든 과목 수만큼 반복
                //DTO에 과목 추가(default : recommend=false, complete=0, chosen=false)

                //추천 과목 수만큼 반복
                //DTO에 추천 과목은 recommend를 true로 변경, chosen을 true로 변경

                //이미 들은 과목 수만큼 반복
                //DTO에 이미 들은 과목은 complete를 들은 학기로 숫자 변경, chosen을 true로 변경
            }
        }
        //반복문 종료
    }
}
