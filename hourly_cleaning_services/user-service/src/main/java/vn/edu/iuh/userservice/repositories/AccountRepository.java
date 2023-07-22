package vn.edu.iuh.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.userservice.models.User;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {
    User findUserByPhone(String phone);
}