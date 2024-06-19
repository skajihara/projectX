-- Drop tables if exist
DROP TABLE IF EXISTS ACCOUNTS;
DROP TABLE IF EXISTS TWEETS;
DROP TABLE IF EXISTS SCHEDULED_TWEETS;
DROP TABLE IF EXISTS BATCH_HISTORY;


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
    execution_start TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_end TIMESTAMP,
    succeeded BOOLEAN NOT NULL DEFAULT false
);

-- Table for JobInstance
CREATE TABLE BATCH_JOB_INSTANCE  (
    JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT,
    JOB_NAME VARCHAR(100) NOT NULL,
    JOB_KEY VARCHAR(32) NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
);

-- Table for JobExecution
CREATE TABLE BATCH_JOB_EXECUTION  (
    JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT,
    JOB_INSTANCE_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP,
    END_TIME TIMESTAMP,
    STATUS VARCHAR(10),
    EXIT_CODE VARCHAR(2500),
    EXIT_MESSAGE VARCHAR(2500),
    LAST_UPDATED TIMESTAMP,
    JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
    constraint JOB_EXEC_INST_FK foreign key (JOB_INSTANCE_ID)
    references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
);

-- Table for JobExecutionParams
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
    JOB_EXECUTION_ID BIGINT NOT NULL ,
    TYPE_CD VARCHAR(6) NOT NULL ,
    KEY_NAME VARCHAR(100) NOT NULL ,
    STRING_VAL VARCHAR(250) ,
    DATE_VAL TIMESTAMP DEFAULT NULL ,
    LONG_VAL BIGINT ,
    DOUBLE_VAL DOUBLE PRECISION ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

-- Table for StepExecution
CREATE TABLE BATCH_STEP_EXECUTION  (
    STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT NOT NULL,
    STEP_NAME VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID BIGINT NOT NULL,
    START_TIME TIMESTAMP NOT NULL ,
    END_TIME TIMESTAMP ,
    STATUS VARCHAR(10) ,
    COMMIT_COUNT BIGINT ,
    READ_COUNT BIGINT ,
    FILTER_COUNT BIGINT ,
    WRITE_COUNT BIGINT ,
    READ_SKIP_COUNT BIGINT ,
    WRITE_SKIP_COUNT BIGINT ,
    PROCESS_SKIP_COUNT BIGINT ,
    ROLLBACK_COUNT BIGINT ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

-- Table for StepExecutionContext
CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
    STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT CLOB,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
    references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
);

-- Table for JobExecutionContext
CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
    JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT CLOB,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

-- Table for StepExecutionSeq
CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;

-- Table for JobExecutionSeq
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;

-- Table for JobSeq
CREATE SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;