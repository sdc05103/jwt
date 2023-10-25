package hello.hellospring.service;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import hello.hellospring.dto.AllClassDTO;
import hello.hellospring.dto.CompleteDTO;
import hello.hellospring.dto.GuideDTO;
import hello.hellospring.repository.GuideRepository;
import org.springframework.context.annotation.Configuration;
import hello.hellospring.repository.GuideRepository;

import java.util.List;

//public List<String> getMajor(String major_name)
@Configuration
public class GuideService {


    private final GuideRepository guideRepository;

    public GuideService(hello.hellospring.repository.GuideRepository guideRepository){
        this.guideRepository = guideRepository;
    }

    public List<AllClassDTO> getAllClass(String major) {
        return guideRepository.getAllClass(major);
    }


    public GuideDTO getAllTotalguide(String id, String major){
        return guideRepository.getAllTotalguide(id, major);
    }

    public List<String> showAll(String id){
        return guideRepository.getMajor(id);
    }

    public String getSID(String sid){
        return guideRepository.getSID(sid);
    }

    public List<String> getSubjectList(String major){
        return guideRepository.getSubjectList(major);
    }
    public List<CompleteDTO> getCompleteClass(String id) { return guideRepository.getCompleteClass(id);
    }
}
