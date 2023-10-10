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

        List<GuideDTO> GuideList;




    }

}
