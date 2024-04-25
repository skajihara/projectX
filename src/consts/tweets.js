import { ref } from 'vue'
export const tweets = ref([
  {
    content: '富山のホタルイカ、最高🍻',
    userId: 'user_A',
    datetime: '2024-03-01 15:30:00',
    location: '富山県滑川市'
  },
  {
    content: '夜間はライトアップも実施「令和6年度 八女黒木大藤まつり」開催！',
    userId: 'user_B',
    datetime: '2024-03-03 11:23:55',
    location: '福岡県八女市'
  },
  {
    content: 'プレゼントキャンペーン🎁',
    userId: 'user_C',
    datetime: '2024-03-10 00:21:51',
    location: '東京都江東区'
  },
  {
    content: 'ガチャ爆死したなう',
    userId: 'user_D',
    datetime: '2024-03-18 20:10:01',
    location: '東京都江東区'
  },
  {
    content: 'コカ・コーラ 500ml×24本がクーポンと定期お得便で1691円に #広告',
    userId: 'user_E',
    datetime: '2024-03-29 15:30:11',
    location: ''
  },
  {
    content: '急速に溶けていくギルバトのモチベ',
    userId: 'user_F',
    datetime: '2024-03-30 01:04:43',
    location: '神奈川県横浜市'
  },
  {
    content:
      '【おはようございます】今日もいい天気ですね！朝から元気に出勤中です。渋谷区はいつも賑やかですね♪ #朝活 #渋谷',
    userId: 'user_C',
    datetime: '2023-07-12 23:01:39',
    location: '東京都渋谷区'
  },
  {
    content:
      '【お昼ごはん】宇都宮市の有名なラーメン屋さんに行ってきました！美味しかったです♪ #ラーメン #宇都宮',
    userId: 'user_20',
    datetime: '2023-09-13 14:26:56',
    location: '栃木県宇都宮市'
  },
  {
    content:
      '【最近のお気に入り】新潟市のカフェで見つけた素敵な本、読み終わりました！おすすめです！ #読書 #新潟',
    userId: 'user_3',
    datetime: '2023-09-09 15:36:57',
    location: '新潟県新潟市'
  },
  {
    content: '【夜の八戸市】夜景が綺麗な八戸市、今日も癒されました♪ #夜景 #八戸',
    userId: 'user_A',
    datetime: '2023-07-30 00:51:59',
    location: '青森県八戸市'
  },
  {
    content: '【湖畔の朝】彦根市の湖畔で朝ランニング、気持ちいいですね！ #朝ラン #彦根',
    userId: 'user_8',
    datetime: '2023-08-31 09:41:46',
    location: '滋賀県彦根市'
  },
  {
    content: '【福井市の風景】福井市の公園でのんびり過ごしています。 #公園 #福井',
    userId: 'user_2',
    datetime: '2023-08-23 10:28:52',
    location: '福井県福井市'
  },
  {
    content: '【神戸市でランチ】神戸市で美味しいお店を見つけました！ #ランチ #神戸',
    userId: 'user_25',
    datetime: '2023-10-11 12:13:07',
    location: '兵庫県神戸市'
  },
  {
    content: '【お昼休みの小松市】小松市の公園でお昼休み中です。 #お昼休み #小松',
    userId: 'user_G',
    datetime: '2023-06-01 16:24:08',
    location: '石川県小松市'
  },
  {
    content: '【熊本市の街角】熊本市の街角にあるカフェでコーヒーを飲んでいます。 #カフェ #熊本',
    userId: 'user_17',
    datetime: '2023-01-18 03:48:03',
    location: '熊本県熊本市'
  },
  {
    content: '【米沢市の観光】米沢市の観光地を散策中です！ #観光 #米沢',
    userId: 'user_8',
    datetime: '2023-06-01 03:29:09',
    location: '山形県米沢市'
  },
  {
    content: '【秋田県横手市で遊ぶ】秋田県横手市の遊園地に来ました！楽しいです♪ #遊園地 #秋田',
    userId: 'user_16',
    datetime: '2024-04-01 08:14:19',
    location: '秋田県横手市'
  },
  {
    content: '【郡山市の雪景色】郡山市でのんびり雪景色を楽しんでいます。 #雪 #郡山',
    userId: 'user_8',
    datetime: '2024-01-31 23:44:14',
    location: '福島県郡山市'
  },
  {
    content: '【高崎市の夜景】高崎市の夜景が綺麗ですね！ #夜景 #高崎',
    userId: 'user_14',
    datetime: '2023-01-29 06:03:18',
    location: '群馬県高崎市'
  },
  {
    content: '【沼津市の海】沼津市の海岸で波の音を聞きながら散歩しています。 #海 #沼津',
    userId: 'user_25',
    datetime: '2023-12-22 08:56:50',
    location: '静岡県沼津市'
  },
  {
    content: '【旭川市でカフェ】旭川市のカフェで友達とおしゃべり中♪ #カフェ #旭川',
    userId: 'user_4',
    datetime: '2023-11-13 02:16:13',
    location: '北海道旭川市'
  },
  {
    content: '【堺市の夜】堺市の夜景がとても幻想的です！ #夜景 #堺',
    userId: 'user_G',
    datetime: '2023-12-04 00:45:58',
    location: '大阪府堺市'
  },
  {
    content: '【彦根市のお祭り】彦根市のお祭りに参加しています！ #お祭り #彦根',
    userId: 'user_4',
    datetime: '2023-01-13 14:01:38',
    location: '滋賀県彦根市'
  },
  {
    content: '【島原市の温泉】島原市の温泉で癒されてきました♪ #温泉 #島原',
    userId: 'user_20',
    datetime: '2023-06-21 22:31:58',
    location: '長崎県島原市'
  },
  {
    content: '【大分市の夕日】大分市の海岸で夕日を見ています！ #夕日 #大分',
    userId: 'user_13',
    datetime: '2023-09-29 13:39:27',
    location: '大分県大分市'
  },
  {
    content:
      '【熊本市のイルミネーション】熊本市のイルミネーションが素敵ですね！ #イルミネーション #熊本',
    userId: 'user_30',
    datetime: '2023-05-04 07:56:16',
    location: '熊本県熊本市'
  },
  {
    content: '【大和高田市の公園】大和高田市の公園でピクニックしています♪ #ピクニック #大和高田',
    userId: 'user_27',
    datetime: '2023-08-15 02:47:08',
    location: '奈良県大和高田市'
  },
  {
    content: '【丸亀市の名物】丸亀市の讃岐うどんを食べています！ #うどん #丸亀',
    userId: 'user_5',
    datetime: '2023-12-08 03:37:37',
    location: '香川県丸亀市'
  },
  {
    content: '【島原市の海岸】島原市の海岸でサーフィンを楽しんでいます！ #サーフィン #島原',
    userId: 'user_8',
    datetime: '2023-01-09 07:24:15',
    location: '長崎県島原市'
  },
  {
    content: '【川口市のイベント】川口市で開催されているイベントに参加しています！ #イベント #川口',
    userId: 'user_24',
    datetime: '2024-02-23 23:45:08',
    location: '埼玉県川口市'
  },
  {
    content: '【姫路市の観光】姫路市の城を見学中です！ #観光 #姫路',
    userId: 'user_21',
    datetime: '2023-12-21 09:04:59',
    location: '兵庫県姫路市'
  },
  {
    content:
      '【名古屋市のショッピング】名古屋市のショッピングモールでお買い物しています♪ #ショッピング #名古屋',
    userId: 'user_11',
    datetime: '2023-12-20 12:19:27',
    location: '愛知県名古屋市'
  },
  {
    content: '【鹿児島市のグルメ】鹿児島市の居酒屋で美味しい料理を楽しんでいます！ #居酒屋 #鹿児島',
    userId: 'user_29',
    datetime: '2023-03-05 21:28:45',
    location: '鹿児島県鹿児島市'
  },
  {
    content: '【長野市の雪】長野市で雪だるまを作っています！ #雪だるま #長野',
    userId: 'user_9',
    datetime: '2024-01-15 17:33:29',
    location: '長野県長野市'
  },
  {
    content: '【水戸市の公園】水戸市の公園で散歩中です。 #散歩 #水戸',
    userId: 'user_D',
    datetime: '2023-11-09 08:24:59',
    location: '茨城県水戸市'
  },
  {
    content: '【姫路市の観光】姫路市の城を見学中です！ #観光 #姫路',
    userId: 'user_21',
    datetime: '2023-12-21 09:04:59',
    location: '兵庫県姫路市'
  },
  {
    content:
      '【名古屋市のショッピング】名古屋市のショッピングモールでお買い物しています♪ #ショッピング #名古屋',
    userId: 'user_11',
    datetime: '2023-12-20 12:19:27',
    location: '愛知県名古屋市'
  },
  {
    content: '【鹿児島市のグルメ】鹿児島市の居酒屋で美味しい料理を楽しんでいます！ #居酒屋 #鹿児島',
    userId: 'user_29',
    datetime: '2023-03-05 21:28:45',
    location: '鹿児島県鹿児島市'
  },
  {
    content: '【長野市の雪】長野市で雪だるまを作っています！ #雪だるま #長野',
    userId: 'user_9',
    datetime: '2024-01-15 17:33:29',
    location: '長野県長野市'
  },
  {
    content: '【水戸市の公園】水戸市の公園で散歩中です。 #散歩 #水戸',
    userId: 'user_D',
    datetime: '2023-11-09 08:24:59',
    location: '茨城県水戸市'
  },
  {
    content: '【秋田市の祭り】秋田市の祭りに来ました！たくさんの人が楽しんでいます♪ #秋田 #祭り',
    userId: 'user_16',
    datetime: '2023-08-12 18:22:14',
    location: '秋田県秋田市'
  },
  {
    content: '【静岡市の花火大会】静岡市の花火大会に友達と来ました！素敵な夜です♪ #花火大会 #静岡',
    userId: 'user_B',
    datetime: '2023-07-05 21:59:08',
    location: '静岡県静岡市'
  },
  {
    content: '【富山市のグルメ】富山市の海鮮丼を食べています！美味しいです♪ #海鮮丼 #富山',
    userId: 'user_15',
    datetime: '2023-04-19 13:08:03',
    location: '富山県富山市'
  },
  {
    content: '【福岡市のカフェ】福岡市のカフェで友達とお茶しています♪ #カフェ #福岡',
    userId: 'user_23',
    datetime: '2023-09-28 15:02:22',
    location: '福岡県福岡市'
  },
  {
    content: '【広島市の夜景】広島市の夜景を見に来ました！ロマンチックですね♪ #夜景 #広島',
    userId: 'user_28',
    datetime: '2023-11-02 22:14:38',
    location: '広島県広島市'
  },
  {
    content: '【岡山市の博物館】岡山市の博物館で歴史を学んでいます！ #博物館 #岡山',
    userId: 'user_7',
    datetime: '2023-10-25 11:39:08',
    location: '岡山県岡山市'
  },
  {
    content: '【神戸市の公園】神戸市の公園でお弁当を食べています♪ #お弁当 #神戸',
    userId: 'user_25',
    datetime: '2023-08-17 12:09:31',
    location: '兵庫県神戸市'
  },
  {
    content: '【神戸市の夜景】神戸市の夜景が綺麗ですね！ #夜景 #神戸',
    userId: 'user_25',
    datetime: '2023-11-22 05:36:12',
    location: '兵庫県神戸市'
  },
  {
    content: '【千葉市の公園】千葉市の公園でお花見中です♪ #お花見 #千葉',
    userId: 'user_19',
    datetime: '2023-04-05 18:43:02',
    location: '千葉県千葉市'
  },
  {
    content: '【盛岡市の観光】盛岡市の観光地を巡っています！ #観光 #盛岡',
    userId: 'user_10',
    datetime: '2023-09-19 20:51:11',
    location: '岩手県盛岡市'
  },
  {
    content: '【大阪市のグルメ】大阪市のたこ焼き屋さんでたこ焼きを食べています！ #たこ焼き #大阪',
    userId: 'user_22',
    datetime: '2023-03-15 14:22:59',
    location: '大阪府大阪市'
  },
  {
    content: '【福岡市の温泉】福岡市の温泉でリフレッシュしています♪ #温泉 #福岡',
    userId: 'user_23',
    datetime: '2023-11-30 09:37:43',
    location: '福岡県福岡市'
  },
  {
    content: '【広島市の公園】広島市の公園で遊んでいます♪ #公園 #広島',
    userId: 'user_28',
    datetime: '2023-01-17 17:55:38',
    location: '広島県広島市'
  },
  {
    content: '【岡山市のグルメ】岡山市の牛丼を食べています！ #牛丼 #岡山',
    userId: 'user_7',
    datetime: '2023-06-08 13:49:14',
    location: '岡山県岡山市'
  },
  {
    content:
      '【千葉市の花火大会】千葉市の花火大会に友達と行ってきました！素敵な夜です♪ #花火大会 #千葉',
    userId: 'user_19',
    datetime: '2023-07-24 21:03:59',
    location: '千葉県千葉市'
  },
  {
    content: '【盛岡市の雪景色】盛岡市の雪景色が綺麗ですね♪ #雪景色 #盛岡',
    userId: 'user_10',
    datetime: '2024-01-09 08:14:29',
    location: '岩手県盛岡市'
  },
  {
    content: '【大阪市のグルメ】大阪市のたこ焼き屋さんでたこ焼きを食べています！ #たこ焼き #大阪',
    userId: 'user_22',
    datetime: '2023-03-15 14:22:59',
    location: '大阪府大阪市'
  },
  {
    content: '【福岡市の温泉】福岡市の温泉でリフレッシュしています♪ #温泉 #福岡',
    userId: 'user_23',
    datetime: '2023-11-30 09:37:43',
    location: '福岡県福岡市'
  },
  {
    content: '【広島市の公園】広島市の公園で遊んでいます♪ #公園 #広島',
    userId: 'user_28',
    datetime: '2023-01-17 17:55:38',
    location: '広島県広島市'
  },
  {
    content: '【岡山市のグルメ】岡山市の牛丼を食べています！ #牛丼 #岡山',
    userId: 'user_7',
    datetime: '2023-06-08 13:49:14',
    location: '岡山県岡山市'
  },
  {
    content:
      '【千葉市の花火大会】千葉市の花火大会に友達と行ってきました！素敵な夜です♪ #花火大会 #千葉',
    userId: 'user_19',
    datetime: '2023-07-24 21:03:59',
    location: '千葉県千葉市'
  },
  {
    content: '【盛岡市の雪景色】盛岡市の雪景色が綺麗ですね♪ #雪景色 #盛岡',
    userId: 'user_10',
    datetime: '2024-01-09 08:14:29',
    location: '岩手県盛岡市'
  }
])
