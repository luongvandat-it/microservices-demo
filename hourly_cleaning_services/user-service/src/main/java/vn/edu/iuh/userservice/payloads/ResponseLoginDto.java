package vn.edu.iuh.userservice.payloads;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.iuh.userservice.models.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDto {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean gender;
    private String roles;
}
