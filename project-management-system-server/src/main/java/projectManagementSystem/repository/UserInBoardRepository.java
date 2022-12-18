package projectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.UserInBoard;

import java.util.List;

@Repository
public interface UserInBoardRepository extends JpaRepository<UserInBoard, Long> {
    @Query("SELECT u FROM UserInBoard u WHERE u.board=?1 AND u.user=?2")
    List<UserInBoard> findByBoardAndUser(Board board, User user);

    @Transactional
    @Modifying
    @Query("delete from UserInBoard u where u.board = ?1")
    int deleteByBoard(Board board);

    @Transactional
    @Modifying
    @Query("delete from UserInBoard u where u.user = ?1")
    int deleteByUser(User user);
}
