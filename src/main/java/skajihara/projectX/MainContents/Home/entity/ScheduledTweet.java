package skajihara.projectX.MainContents.Home.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "SCHEDULED_TWEETS")
public class ScheduledTweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_id", nullable = false, length = 10)
    private String accountId;

    @Column(nullable = false, length = 200)
    private String text;

    @Column(length = 100)
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Date scheduled_datetime;

    @Column(length = 50)
    private String location;

    @Column(nullable = false)
    private boolean deleteFlag = false;
}
