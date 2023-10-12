package hello.hellospring.controller;

import hello.hellospring.dto.CreditEditDTO;
import hello.hellospring.dto.GuideDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

public class GuideController {

    @GetMapping(value = "/api/guide")
    public List<GuideDTO> creditShow(@RequestHeader("Authorization") String token) {

        String id = Jwts.parserBuilder()
                .setSigningKey("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK" .getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();


        //채윤 코드
        //로그인 된 유저의 추천 major 받아오는 코드


        //더미데이터
        String major1 = "컴퓨터공학과";
        String major2 = "인공지능학과";
        String major3 = "소프트웨어학과";


        //major가 1개인지 3개인지 검사
        //major 개수만큼 실행하는 반복문(1 or 3)

            //IF : 유저의 가이드 데이터가 디비에 있는 경우 그대로 리턴
                // List<GuideDTO> GuideList = 디비에서 받아오는 코드;
                // return GuideList;

            //ELSE : 그렇지 않은 경우 생성
                //이미 들은 과목 가져오기 - (class_list, class, subject 조인)
                //해당 전공에 추천하는 과목 다 받아오기 (major_list에서 받아온 다음, 파싱 작업 후 class, subject 조인해서 각 과목에 해당하는 학점, 계열 가져오기)
                //모든 과목 가져오기 - (class, subject 조인)

                //모든 과목 수만큼 반복
                    //DTO에 과목 추가(default : recommend=false, complete=0, chosen=false)

                //추천 과목 수만큼 반복
                    //DTO에 추천 과목은 recommend를 true로 변경, chosen을 true로 변경

                //이미 들은 과목 수만큼 반복
                    //DTO에 이미 들은 과목은 complete를 들은 학기로 숫자 변경, chosen을 true로 변경

        //반복문 종료
    }
}
