package spring.auth.dto.response;

import java.util.List;


public record JwtResponse(String accessToken, String type, Long id, String username, String email, String role) {

   public JwtResponse(String accessToken, Long id, String username, String email, String role) {
       this(accessToken, "Bearer", id, username, email, role);
   }

}