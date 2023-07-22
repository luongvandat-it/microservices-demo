package vn.edu.iuh.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean gender;
}
