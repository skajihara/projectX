package skajihara.projectX.MainContents.Home.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "BATCH_HISTORY")
public class BatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_processed_tweet_id", nullable = false)
    private int lastProcessedTweetId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "execution_start", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date executionStart = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "execution_end", columnDefinition = "TIMESTAMP")
    private Date executionEnd;

    @Column(nullable = false)
    private boolean succeeded = false;
}