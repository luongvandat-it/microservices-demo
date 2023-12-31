package vn.edu.iuh.authservice.entities.value_objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserVO {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean gender;
    private String roles;
}
