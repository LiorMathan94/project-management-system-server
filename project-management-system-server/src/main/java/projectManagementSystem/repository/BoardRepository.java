package projectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectManagementSystem.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}