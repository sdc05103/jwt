package hello.hellospring.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import hello.hellospring.dto.AllClassDTO;
import hello.hellospring.dto.CompleteDTO;
import hello.hellospring.dto.GuideDTO;
import hello.hellospring.dto.SubjectDataDTO;
import hello.hellospring.service.GuideService;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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


        //로그인 된 유저의 추천 major 받아오는 코드
        List<String> major_list = guideService.showAll(id);

        //major가 1개인지 3개인지 검사(list 요소의 개수 확인하는 함수)
        int major_num = major_list.size();
        List<GuideDTO> GuideList = new ArrayList<>();

        //major 개수만큼 실행하는 반복문(1 or 3)
        for(int i=0 ; i<major_num; i++) {

            String major = major_list.get(i);

            //IF : 유저의 가이드 데이터가 디비에 있는 경우 그대로 리턴 //total_guide 테이블에 현재 사용자 id 있는지 check
            if(id.equals(guideService.getSID(id))) { //로그인한 사용자의 id와 total_guide의 sid가 같다면

                  GuideDTO Guide_element = guideService.getAllTotalguide(id, major);
                  GuideList.add(Guide_element);

            }

            //ELSE : 그렇지 않은 경우 생성
            else {

                //모든 과목 가져오기 - (class, subject 조인)
                List<AllClassDTO> AllClassList = guideService.getAllClass(major);


                //채윤 - 10/25 완료
                //공통과목 디비에 넣기 10/25 완료
                //subject 테이블에 '국어0', '영어0', '수학0'처럼 끝자리가 0인 subject 행을 생성하신 후
                //해당 subject 행의 sid에 매칭하여서
                //class 테이블에 공통과목을 넣어주세요.

                //진현 - 10/21 완료
                //이미 들은 과목 가져오기 - (class_list, class, subject 조인)
                List<CompleteDTO> CompleteList = guideService.getCompleteClass(id);
                for(int j=0 ; j< CompleteList.size() ; j++) {
                    System.out.println(CompleteList.get(j).getClass_name());
                }

                //채윤
                //해당 전공에 추천하는 과목 다 받아오기 (major_detail에서 받아온 다음, 파싱 작업 후 class, subject 조인해서 각 과목에 해당하는 학점, 계열 가져오기)
                List<String> subject_list = guideService.getSubjectList(major);

//                for(int j=0 ; j< subject_list.size() ; j++) {
//                    System.out.println(subject_list.get(j));
//                }

                //진현 - 10/21 완료
                //모든 과목 수만큼 반복
                //guideDTO에 과목 추가(default : recommend=false, complete=0, chosen=false)
                List<SubjectDataDTO> subjectDataDTOList = new ArrayList<>();

                for (int j = 0; j < AllClassList.size(); j++) {
                    SubjectDataDTO subjectDataDTO = new SubjectDataDTO();
                    AllClassDTO allClassDTO = AllClassList.get(j);

                    subjectDataDTO.setCategory(split_head(allClassDTO.getSubject_name()));
                    subjectDataDTO.setSubject(split_head(allClassDTO.getSubject_name()));
                    subjectDataDTO.setClasses(allClassDTO.getClass_name());
                    subjectDataDTO.setCredit(allClassDTO.getCredit());
                    subjectDataDTO.setCourse(getCourse(split_tail(allClassDTO.getSubject_name())));
                    subjectDataDTO.setComplete(0);

                    //공통과목인 경우
                    if(getCourse(split_tail(allClassDTO.getSubject_name()))=="공통"){
                        subjectDataDTO.setRecommend(true);
                        subjectDataDTO.setChosen(true);
                    }

                    //일반, 진로과목인 경우
                    else {
                        subjectDataDTO.setRecommend(false);
                        subjectDataDTO.setChosen(false);
                    }

                    subjectDataDTOList.add(subjectDataDTO);
                }

                GuideDTO guideDTO = new GuideDTO(major, subjectDataDTOList);
                GuideList.add(guideDTO);

                //진현 - 10/27 완료
                //total guide list를 임시로 디비에 넣어놓기
                guideService.insertTemporaryGuide(major, id, subjectDataDTOList);




                //채윤
                //추천 과목 수만큼 반복
                //DTO에 추천 과목은 recommend를 true로 변경, chosen을 true로 변경




                //진현 - 10/27 완료
                //이미 들은 과목 수만큼 반복
                //DTO에 이미 들은 과목은 complete를 들은 학기로 숫자 변경, chosen을 true로 변경
                guideService.applyCompleteList(major, id, CompleteList);

                //진현 - 10/27 완료
                //테스트 실행할 때마다 total_guide에 데이터가 쌓이는 것을 방지하기 위한 코드. 최종 완료 시에는 삭제할 예정
                //임시 total_guide 삭제
                guideService.deleteTemporaryGuide(major, id);


                //디비에 저장한 코드를 GuideList에 받아오는 코드

            }
        }
        //반복문 종료
        return GuideList;

    }


    //post mapping method implement


    public static String split_head(String input) {
        if (input == null || input.isEmpty()) {
            return ""; // 빈 문자열이나 null일 경우 빈 문자열을 반환
        } else {
            return input.substring(0, input.length() - 1);
        }
    }

    public static char split_tail(String input) {
            int len = input.length();
            char last = input.charAt(len - 1); // 마지막 문자 추출
            return last; // 마지막 문자를 문자열로 변환하여 반환
    }

    public static String getCourse(char num){
        char zero = '0';
        char one = '1';
        char two = '2';

        if(num == zero){return "공통";}
        else if (num == one) {return "일반";}
        else{return "진로";}
    }

}

