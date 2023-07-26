package vn.edu.iuh.gatewayservice.entities.value_objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

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
