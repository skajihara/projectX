package skajihara.projectX.MainContents.Home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;

import java.util.List;

@Repository
public interface ScheduledTweetRepository extends JpaRepository<ScheduledTweet, Integer> {

    @Query("SELECT st FROM ScheduledTweet st WHERE st.accountId = :account_id AND st.deleteFlag = false ORDER BY st.scheduledDatetime ASC, st.createdDatetime DESC")
    List<ScheduledTweet> selectScheduledTweets(@Param("account_id") String accountId);

    @Query("SELECT st FROM ScheduledTweet st WHERE st.id = :schedule_id AND st.deleteFlag = false")
    ScheduledTweet selectScheduledTweet(@Param("schedule_id") int schedule_id);
}