package skajihara.projectX.MainContents.Home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skajihara.projectX.MainContents.Home.entity.Tweet;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet,Integer> {

    @Query("SELECT t FROM Tweet t WHERE t.deleteFlag = false ORDER BY t.datetime DESC")
    List<Tweet> selectAll();

    @Query("SELECT t FROM Tweet t WHERE t.deleteFlag = false ORDER BY t.datetime DESC LIMIT :num")
    List<Tweet> selectRecentN(@Param("num") int num);

    @Query("SELECT t FROM Tweet t WHERE t.accountId = :account_id AND t.deleteFlag = false ORDER BY t.datetime DESC LIMIT 20")
    List<Tweet> selectTweetsByAccountId(@Param("account_id") String account_id);

    @Query("SELECT t FROM Tweet t WHERE t.id = :id AND t.deleteFlag = false")
    Tweet selectTweet(@Param("id") int id);
}
