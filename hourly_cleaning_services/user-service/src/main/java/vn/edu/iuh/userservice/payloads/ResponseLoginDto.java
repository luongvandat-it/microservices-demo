package vn.edu.iuh.userservice.payloads;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDto {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean gender;
}
