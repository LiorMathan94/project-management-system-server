package projectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectManagementSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("delete from User u where u.id = ?1")
    int deleteById(long id);

    @Transactional
    @Modifying
    @Query("update User u set u.name = ?2 where u.id = ?1")
    int updateUserNameById(long id, String name);

    @Transactional
    @Modifying
    @Query("update User u set u.email = ?2 where u.id = ?1")
    int updateUserEmailById(long id, String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.id = ?1")
    int updateUserPasswordById(long id, String password);
}
