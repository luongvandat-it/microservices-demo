package vn.edu.iuh.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
        List<String> roles = new ArrayList<>();
        roles.add("1");
        roles.add("2");
        System.out.println(roles.toString());
    }

}
