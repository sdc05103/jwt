package hello.hellospring.controller;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import hello.hellospring.dto.CreditEditDTO;
import hello.hellospring.service.CreditsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CreditsController {

    private final CreditsService creditsService;

    @ResponseBody
    @RequestMapping(value = "/api/credits/{semester}")
    public ResponseEntity<String> creditEdit(@PathVariable int semester, @RequestBody List<CreditEditDTO> creditList) {

        List<String> classNames = new ArrayList<>();
        List<Integer> credits = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        for (CreditEditDTO crediteditDTO : creditList) {
            classNames.add(crediteditDTO.getClass_name());
            credits.add(crediteditDTO.getCredit());
            ids.add(crediteditDTO.getId());
            subjects.add(crediteditDTO.getSubject());
        }

        creditsService.credit_delete(semester);

        for (int i = 0; i < creditList.size(); i++) {
            //Credit object를 만든 뒤, Credit Object attribute에 하나씩 저장
            Credit credit_obj = new Credit();

            int cid = creditsService.find_cid(classNames.get(i)); //String class_name으로 int cid 검색
            credit_obj.setCid(cid);

            int sid = creditsService.find_sid(classNames.get(i)); //String subject로 int sid 검색
            credit_obj.setSid(sid);

            credit_obj.setCredit(credits.get(i));
            credit_obj.setSemester(semester);

            creditsService.credit_edit(semester, credit_obj);
        }
        return ResponseEntity.ok("Successfully processed credit data");
    }

    @GetMapping(value = "/api/credits/{semester}")
    public List<CreditEditDTO> creditShow(@PathVariable int semester) {

        List<CreditEditDTO> creditList = new ArrayList<>();

        List<String> classNames = new ArrayList<>();
        List<Integer> credits = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        int sem = semester;
        String id = "12";

        //List<Credit> credit = creditsService.showAll(sem, id);

        List<Credit> credit_list = creditsService.showAll(sem, id);

        //credits = class_list 테이블에서 데이터 받아옴
        for (Credit credit : credit_list) { //classNames, subjects 이름 받아오는 반복문
            int cid = credit.getCid();
            List<Classes> data = creditsService.showAll_class(cid);
            classNames.add((data.get(0).getName()));
            //classNames에 class 이름 받아옴. ex) '화법과 작문' ...

            List<Subject> data2 = creditsService.showAll_subject(data.get(0).getSid());
            subjects.add( data2.get(0).getName());
            //subjects에 subject 이름 받아옴. ex) '국어' ...
            int sub_length = data2.get(0).getName().length();

            CreditEditDTO creditDto = new CreditEditDTO("1", data.get(0).getName(), credit.getCredit(), data2.get(0).getName().substring(0, sub_length-1));
            creditList.add(creditDto);

        }
        return creditList;
    }

    @Autowired
    public CreditsController(CreditsService creditsService) {
        this.creditsService = creditsService;
    }


//    // 드롭다운 데이터를 가져오는 메소드 (1학년 1학기, ... , 3학년 2학기)
//    private List<String> getDropdownOptions_semester() {
//        List<String> dropdownOptions = new ArrayList<>();
//
//        // 1학년 1학기부터 3학년 2학기까지의 선택지 생성
//        for (int year = 1; year <= 3; year++) {
//            for (int semester = 1; semester <= 2; semester++) {
//                String option = year + "학년 " + semester + "학기";
//                dropdownOptions.add(option);
//            }
//        }
//        return dropdownOptions;
//    }

    // 드롭다운 데이터를 가져오는 메소드 (국어, 수학, 영어, ...)
//    private List<String> getDropdownOptions_subject(){
//        List<String> dropdownOptions = new ArrayList<>();
//        List<Subject> subjects = creditsService.showAll_subject(-1);
//
//        for(Subject subject : subjects){
//            dropdownOptions.add(subject.getName());
//        }
//
//        return dropdownOptions;
//    }

//    private List<String> getDropdownOptions_class(){
//        List<String> dropdownOptions = new ArrayList<>();
//        List<Classes> classes = creditsService.showAll_class(-1);
//
//        for(classes classes : classes){
//            dropdownOptions.add(subject.getName());
//        }
//
//        return dropdownOptions;
//    }

//    private int convertSemester(String semester) {
//        switch (semester) {
//            case "1학년 1학기":
//                return 1;
//            case "1학년 2학기":
//                return 2;
//            case "2학년 1학기":
//                return 3;
//            case "2학년 2학기":
//                return 4;
//            case "3학년 1학기":
//                return 5;
//            case "3학년 2학기":
//                return 6;
//            default:
//                throw new IllegalArgumentException("Invalid semester: " + semester);
//        }
//    }
}