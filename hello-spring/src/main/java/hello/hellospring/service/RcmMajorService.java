package hello.hellospring.service;

import hello.hellospring.domain.RcmMajor;
import hello.hellospring.repository.RcmMajorRepository;
import org.springframework.stereotype.Service;

@Service
public class RcmMajorService {

    private final RcmMajorRepository rcmMajorRepository;

    public RcmMajorService(RcmMajorRepository rcmMajorRepository) {
        this.rcmMajorRepository = rcmMajorRepository;
    }

    public int findMajorId(String major){
        return rcmMajorRepository.findMajorId(major);
    }

    public void RcmMajorEdit(RcmMajor major){
        rcmMajorRepository.RcmMajorEdit(major);
    }

}
