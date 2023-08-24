package hello.hellospring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInTokenDto {
    private String token;

    @Builder
    public SignInTokenDto(String token) {
        this.token = token;
    }
}
