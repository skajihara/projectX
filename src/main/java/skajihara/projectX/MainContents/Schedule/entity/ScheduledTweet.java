package skajihara.projectX.MainContents.Schedule.entity;

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

    @Column(length = 50)
    private String location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="scheduled_datetime", nullable = false, columnDefinition = "TIMESTAMP")
    private Date scheduledDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_datetime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDatetime = new Date();

    @Column(nullable = false)
    private boolean deleteFlag = false;
}
