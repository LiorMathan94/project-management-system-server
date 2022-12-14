package projectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectManagementSystem.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}