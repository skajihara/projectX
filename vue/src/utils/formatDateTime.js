export function formatDateTime(datetimeStr, mode) {
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
    { value: minute }
  ] = dateTimeFormat.formatToParts(date)

  var formattedDateTime = null
  if (mode == 'update') {
    formattedDateTime = `${year}-${month}-${day}T${hour}:${minute}:00.000+09:00`
  } else if (mode == 'display') {
    formattedDateTime = `${year}-${month}-${day} ${hour}:${minute}`
  }

  return formattedDateTime
}
