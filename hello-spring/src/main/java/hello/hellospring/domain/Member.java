package hello.hellospring.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter @Setter
@Entity
@Builder
@Table(name = "member")
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가하도록 지정
    @Column(name = "PID") // 컬럼명 지정
    private Long pid;

    @Column(name = "ID") // 컬럼명 지정
    private String id;

    @Column(name = "NAME") // 컬럼명 지정
    private String name;

    @Column(name = "PWD") // 컬럼명 지정
    private String pwd;

    @Enumerated(EnumType.STRING)
    private Authority role;
    public Member() {
    }

    // 매개변수가 있는 생성자 추가
    public Member(Long pid, String id, String name, String pwd, Authority role) {
        this.pid = pid;
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.role = role;
    }


    public void encodePassword(PasswordEncoder passwordEncoder){
        this.pwd = passwordEncoder.encode(pwd);
    }
    public boolean checkPassword(PasswordEncoder passwordEncoder, String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.pwd);
    }
}
