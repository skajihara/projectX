-- Drop tables if exist
DROP TABLE IF EXISTS TWEETS;
DROP TABLE IF EXISTS ACCOUNTS;

-- Create ACCOUNTS table
CREATE TABLE IF NOT EXISTS ACCOUNTS (
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    bio VARCHAR(200) NOT NULL,
    icon VARCHAR(100) DEFAULT '/src/assets/icons/user/default_icon.svg',
    header_photo VARCHAR(100) DEFAULT '/src/assets/images/header/default_header.jpg',
    location VARCHAR(50),
    birthday DATE CHECK (birthday < CURRENT_DATE),
    registered DATE NOT NULL DEFAULT CURRENT_DATE CHECK (registered <= CURRENT_DATE),
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
