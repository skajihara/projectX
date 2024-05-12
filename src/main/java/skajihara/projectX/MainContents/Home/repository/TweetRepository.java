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
}
//
//@Mapper
//public interface IssueRepository {
//
//    @Select("select * from issues")
//    List<IssueEntity> findAll();
//
//    @Insert("insert into issues (summary, description) values (#{summary}, #{description})")
//    void insert(String summary, String description);
//
//    @Select("select * from issues where id = #{issueId}")
//    IssueEntity findById(long issueId);
//}
