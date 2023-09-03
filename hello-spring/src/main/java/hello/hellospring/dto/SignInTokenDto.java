package hello.hellospring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInTokenDto {
    private String token;
<<<<<<< HEAD
    private String name;

    @Builder
    public SignInTokenDto(String token, String name) {
        this.token = token;
        this.name = name;
=======
    private String username;

    @Builder
    public SignInTokenDto(String token,String username) {
        this.token = token;
        this.username = username;
>>>>>>> c88523a944e23b1df5f6040ccab6fa3c170f4645
    }
}
