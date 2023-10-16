package hello.hellospring.service;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import hello.hellospring.dto.AllClassDTO;
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

    public List<String> showAll(String id){
        System.out.println("service");
        return guideRepository.getMajor(id);
    }
}
