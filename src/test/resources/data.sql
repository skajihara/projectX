INSERT INTO ACCOUNTS (id, name, bio, icon, header_photo, location, birthday, registered, following, follower, valid_flag, delete_flag)
VALUES
('user_A','Test User A','user_a test text.','/src/assets/icons/user/kkrn_icon_user_1.svg','/src/assets/images/header/h01.jpg','test prefectureA','1988-04-10','2016-05-17',5,7,true,false),
('user_B','Test User B','user_b test text.','/src/assets/icons/user/kkrn_icon_user_2.svg','/src/assets/images/header/h02.jpg','test prefectureB','1992-11-01','2017-07-25',3,10,true,false),
('user_C','Test User C','user_c test text.','/src/assets/icons/user/kkrn_icon_user_3.svg','/src/assets/images/header/h03.jpg','test prefectureC','1985-03-13','2011-09-19',8,12,true,false);

INSERT INTO TWEETS (account_id, text, image, likes, retweets, replies, views, datetime, location, delete_flag)
VALUES
('user_A','富山のホタルイカ、最高🍻','/src/assets/images/img02.jpg',9,23,7,14,'2024-03-01T15:30:00.000+00:00','Namegawa City, Toyama Prefecture',false),
('user_B','夜間はライトアップも実施「令和6年度 八女黒木大藤まつり」開催！','/src/assets/images/img03.jpg',301,2,0,124,'2024-03-03T11:23:55.000+00:00','Yame City, Fukuoka Prefecture',false),
('user_C','プレゼントキャンペーン🎁','/src/assets/images/img04.jpg',30133,2322,792,140230,'2024-03-10T00:21:51.000+00:00','Koto-ku, Tokyo',false),
('user_A','ガチャ爆死したなう','/src/assets/images/img01.GIF',13,3,2,140,'2024-03-18T20:10:01.000+00:00','Koto-ku, Tokyo',false),
('user_B','コカ・コーラ 500ml×24本がクーポンと定期お得便で1691円に #広告','/src/assets/images/img01.GIF',23301,232,7333,149934,'2024-03-29T15:30:11.000+00:00','',false),
('user_C','急速に溶けていくギルバトのモチベ','/src/assets/images/img01.GIF',1,0,0,23,'2024-03-30T01:04:43.000+00:00','Yokohama City, Kanagawa Prefecture',false),
('user_A','【おはようございます】今日もいい天気ですね！朝から元気に出勤中です。渋谷区はいつも賑やかですね♪ #朝活 #渋谷','/src/assets/images/img01.GIF',11,3,0,63,'2023-07-12T23:01:39.000+00:00','Shibuya Ward, Tokyo',false),
('user_B','【お昼ごはん】宇都宮市の有名なラーメン屋さんに行ってきました！美味しかったです♪ #ラーメン #宇都宮','/src/assets/images/img05.jpg',27,1,1,29,'2023-09-13T14:26:56.000+00:00','Utsunomiya City, Tochigi',false),
('user_C','【最近のお気に入り】新潟市のカフェで見つけた素敵な本、読み終わりました！おすすめです！ #読書 #新潟','/src/assets/images/img06.jpg',1,2,1,1,'2023-09-09T15:36:57.000+00:00','Niigata City, Niigata Prefecture',false),
('user_A','【夜の八戸市】夜景が綺麗な八戸市、今日も癒されました♪ #夜景 #八戸','/src/assets/images/img01.GIF',31,3,1,148,'2023-07-30T00:51:59.000+00:00','Hachinohe City, Aomori Prefecture',false);

INSERT INTO SCHEDULED_TWEETS (account_id, text, image, location, scheduled_datetime, created_datetime, delete_flag) VALUES
('user_A', 'ツイート内容1', '/src/assets/images/img01.GIF', '東京都', FORMATDATETIME(DATEADD(SECOND, 10, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), false),
('user_A', 'ツイート内容2', '/src/assets/images/img02.jpg', '大阪府', FORMATDATETIME(DATEADD(SECOND, 20, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), false),
('user_B', 'ツイート内容3', '/src/assets/images/img03.jpg', '北海道', FORMATDATETIME(DATEADD(SECOND, 30, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), false);
