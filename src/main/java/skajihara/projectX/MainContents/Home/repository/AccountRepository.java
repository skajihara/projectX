package skajihara.projectX.MainContents.Home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skajihara.projectX.MainContents.Home.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a WHERE a.id = :id AND a.validFlag = true AND a.deleteFlag = false")
    Account selectAccount(@Param("id") String id);
}
