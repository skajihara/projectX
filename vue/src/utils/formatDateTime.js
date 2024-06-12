export function formatDateTime(datetimeStr) {
  const date = new Date(datetimeStr)
  // 日本のタイムゾーンに合わせて日時を変換する設定
  const options = {
    timeZone: 'Asia/Tokyo',
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false
  }
  const dateTimeFormat = new Intl.DateTimeFormat('ja-JP', options)
  const [
    { value: year },
    ,
    { value: month },
    ,
    { value: day },
    ,
    { value: hour },
    ,
    { value: minute },
    ,
    { value: second }
  ] = dateTimeFormat.formatToParts(date)
  const formattedDateTime = `${year}-${month}-${day} ${hour}:${minute}:${second}`

  return formattedDateTime
}
