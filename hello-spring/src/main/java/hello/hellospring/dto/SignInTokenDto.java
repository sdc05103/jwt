package hello.hellospring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInTokenDto {
    private String token;
    private String username;

    @Builder
    public SignInTokenDto(String token,String username) {
        this.token = token;
        this.username = username;
    }
}
