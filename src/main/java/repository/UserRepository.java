package repository;


import model.User;
import model.UserPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserPK> {
}
