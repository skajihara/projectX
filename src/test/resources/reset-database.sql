-- Drop tables if exist
DROP TABLE IF EXISTS TWEETS;
DROP TABLE IF EXISTS SCHEDULED_TWEETS;
DROP TABLE IF EXISTS ACCOUNTS;
DROP TABLE IF EXISTS BATCH_HISTORY;

-- Create ACCOUNTS table
CREATE TABLE IF NOT EXISTS ACCOUNTS (
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    bio VARCHAR(200) NOT NULL,
    icon VARCHAR(100) DEFAULT '/src/assets/icons/user/default_icon.svg',
    header_photo VARCHAR(100) DEFAULT '/src/assets/images/header/default_header.jpg',
    location VARCHAR(50),
    birthday DATE,
    registered DATE NOT NULL DEFAULT CURRENT_DATE,
    following INT NOT NULL DEFAULT 0,
    follower INT NOT NULL DEFAULT 0,
    valid_flag BOOLEAN NOT NULL DEFAULT true,
    delete_flag BOOLEAN NOT NULL DEFAULT false
);

-- Create TWEETS table
CREATE TABLE IF NOT EXISTS TWEETS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id VARCHAR(20) NOT NULL,
    text VARCHAR(200) NOT NULL,
    image VARCHAR(100),
    likes INT NOT NULL DEFAULT 0,
    retweets INT NOT NULL DEFAULT 0,
    replies INT NOT NULL DEFAULT 0,
    views INT NOT NULL DEFAULT 0,
    datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    location VARCHAR(50),
    delete_flag BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (account_id) REFERENCES ACCOUNTS(id)
);

-- Create SCHEDULED_TWEETS table
CREATE TABLE IF NOT EXISTS SCHEDULED_TWEETS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id VARCHAR(20) NOT NULL,
    text VARCHAR(200) NOT NULL,
    image VARCHAR(100),
    location VARCHAR(50),
    scheduled_datetime TIMESTAMP NOT NULL,
    created_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_flag BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (account_id) REFERENCES ACCOUNTS(id)
);

-- Create BATCH_HISTORY table
CREATE TABLE IF NOT EXISTS BATCH_HISTORY (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_processed_tweet_id INT NOT NULL,
    processed_num INT NOT NULL,
    execution_start TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_end TIMESTAMP,
    succeeded BOOLEAN NOT NULL DEFAULT false
);

INSERT INTO ACCOUNTS (id, name, bio, icon, header_photo, location, birthday, registered, following, follower, valid_flag, delete_flag)
VALUES
('user_A','Test User A','user_a test text.','/src/assets/icons/user/kkrn_icon_user_1.svg','/src/assets/images/header/h01.jpg','test prefectureA','1988-04-10','2016-05-17',5,7,true,false),
('user_B','Test User B','user_b test text.','/src/assets/icons/user/kkrn_icon_user_2.svg','/src/assets/images/header/h02.jpg','test prefectureB','1992-11-01','2017-07-25',3,10,true,false),
('user_C','Test User C','user_c test text.','/src/assets/icons/user/kkrn_icon_user_3.svg','/src/assets/images/header/h03.jpg','test prefectureC','1985-03-13','2011-09-19',8,12,true,false);
