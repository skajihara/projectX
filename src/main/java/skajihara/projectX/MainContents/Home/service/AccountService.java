package skajihara.projectX.MainContents.Home.service;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ACCOUNTS")
public class AccountService {
    @Id
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String bio;

    @Column(nullable = false, length = 200)
    private String icon;

    @Column(nullable = false)
    private boolean validFlag = false;

    @Column(nullable = false)
    private boolean deleteFlag = false;
}
