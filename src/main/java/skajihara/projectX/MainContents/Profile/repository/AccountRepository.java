package skajihara.projectX.MainContents.Profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skajihara.projectX.MainContents.Profile.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a WHERE a.validFlag = true AND a.deleteFlag = false ORDER BY a.id DESC")
    List<Account> selectAll();

    @Query("SELECT a FROM Account a WHERE a.id = :id AND a.validFlag = true AND a.deleteFlag = false")
    Account selectAccount(@Param("id") String id);
}
