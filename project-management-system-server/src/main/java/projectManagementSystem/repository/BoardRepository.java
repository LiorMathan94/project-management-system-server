package projectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectManagementSystem.entity.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT distinct b FROM Board b inner join b.authorizedUsers a WHERE a.user.id = ?1")
    List<Board> getBoardsByUser(long userId);
}
