package skajihara.projectX.MainContents.Profile.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @Column(name = "id", length = 20, nullable = false)
    private String id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "bio", length = 200, nullable = false)
    private String bio;

    @Column(name = "icon", length = 100, nullable = false, columnDefinition = "varchar(100) default '/src/assets/icons/user/default_icon.svg'")
    private String icon = "/src/assets/icons/user/default_icon.svg";

    @Column(name = "header_photo", length = 100, nullable = false, columnDefinition = "varchar(100) default '/src/assets/images/header/default_header.jpg'")
    private String headerPhoto = "/src/assets/images/header/default_header.jpg";

    @Column(name = "location", length = 50)
    private String location;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "registered", nullable = false, columnDefinition = "date default current_date")
    private LocalDate registered = LocalDate.now();

    @Column(name = "following", nullable = false, columnDefinition = "int default 0")
    private int following = 0;

    @Column(name = "follower", nullable = false, columnDefinition = "int default 0")
    private int follower = 0;

    @Column(name = "valid_flag", nullable = false, columnDefinition = "boolean default true")
    private boolean validFlag = true;

    @Column(name = "delete_flag", nullable = false, columnDefinition = "boolean default false")
    private boolean deleteFlag = false;
}
