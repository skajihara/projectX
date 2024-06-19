package skajihara.projectX.MainContents.Home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skajihara.projectX.MainContents.Home.entity.BatchHistory;

@Repository
public interface BatchHistoryRepository extends JpaRepository<BatchHistory, Integer> {
    BatchHistory findTopByOrderByIdDesc();
}