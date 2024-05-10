-- Insert 20 records into ACCOUNTS table
INSERT INTO ACCOUNTS (id, name, bio, valid_flag, delete_flag)
VALUES
('0000000001', 'User1', 'Bio for User1', true, false),
('0000000002', 'User2', 'Bio for User2', true, false),
('0000000003', 'User3', 'Bio for User3', true, false),
('0000000004', 'User4', 'Bio for User4', true, false),
('0000000005', 'User5', 'Bio for User5', true, false),
('0000000006', 'User6', 'Bio for User6', true, false),
('0000000007', 'User7', 'Bio for User7', true, false),
('0000000008', 'User8', 'Bio for User8', true, false),
('0000000009', 'User9', 'Bio for User9', true, false),
('0000000010', 'User10', 'Bio for User10', true, false),
('0000000011', 'User11', 'Bio for User11', true, false),
('0000000012', 'User12', 'Bio for User12', true, false),
('0000000013', 'User13', 'Bio for User13', true, false),
('0000000014', 'User14', 'Bio for User14', true, false),
('0000000015', 'User15', 'Bio for User15', true, false),
('0000000016', 'User16', 'Bio for User16', true, false),
('0000000017', 'User17', 'Bio for User17', true, false),
('0000000018', 'User18', 'Bio for User18', true, false),
('0000000019', 'User19', 'Bio for User19', true, false),
('0000000020', 'User20', 'Bio for User20', true, false);

-- Insert 100 records into TWEETS table
INSERT INTO TWEETS (account_id, text, likes, retweets, replies, views, datetime, delete_flag)
SELECT
    (SELECT id FROM ACCOUNTS ORDER BY RANDOM() LIMIT 1),
    'Tweet text for User' || (SELECT id FROM ACCOUNTS ORDER BY RANDOM() LIMIT 1),
    ROUND(RANDOM() * 100),
    ROUND(RANDOM() * 50),
    ROUND(RANDOM() * 20),
    ROUND(RANDOM() * 200),
    NOW() - INTERVAL '1' DAY * ROUND(RANDOM() * 60), -- 60日以内のランダムな日付
    false
FROM
    GENERATE_SERIES(1, 100);
