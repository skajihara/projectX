package skajihara.projectX.MainContents.Home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;

import java.util.List;

@Repository
public interface ScheduledTweetRepository extends JpaRepository<ScheduledTweet,Integer> {

    @Query("SELECT t FROM ScheduledTweet t WHERE t.accountId = :account_id AND t.deleteFlag = false ORDER BY t.datetime DESC")
    List<ScheduledTweet> selectScheduledTweets(@Param("account_id") String account_id);

    @Query("SELECT t FROM ScheduledTweet t WHERE t.id = :schedule_id AND t.deleteFlag = false")
    ScheduledTweet selectScheduledTweet(@Param("schedule_id") int schedule_id);
}
