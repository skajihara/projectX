package skajihara.projectX.MainContents.Home.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @Column(nullable = false, length = 20)
    private String id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(length = 200)
    private String bio;

    @Column(length = 100)
    private String icon;

    @Column(nullable = false)
    private boolean validFlag = true;

    @Column(nullable = false)
    private boolean deleteFlag = false;
}
