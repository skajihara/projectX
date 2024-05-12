package skajihara.projectX.MainContents.Home.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "TWEETS")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_id", nullable = false, length = 10)
    private String accountId;

    @Column(nullable = false, length = 200)
    private String text;

    @Column(length = 100)
    private String image;

    @Column(nullable = false)
    private int likes = 0;

    @Column(nullable = false)
    private int retweets = 0;

    @Column(nullable = false)
    private int replies = 0;

    @Column(nullable = false)
    private int views = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date datetime = new Date();

    @Column(length = 50)
    private String location;

    @Column(nullable = false)
    private boolean deleteFlag = false;
}