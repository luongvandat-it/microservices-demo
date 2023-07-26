package vn.edu.iuh.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean gender;
    private String accessToken;
    private String refreshToken;
}
