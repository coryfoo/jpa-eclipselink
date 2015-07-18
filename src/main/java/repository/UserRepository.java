package repository;


import model.User;
import model.UserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UserPK> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findById(Long id);
}
