fun main() {
    agoToText()
    getCommission("Mastercard", 0, 80_000)
    getCommission("Visa", 40, 100)
    getCommission("VK Pay", 40_000, 15000)
}
fun agoToText() {
    val seconds = 61
    var text = ""
    val minutes = Math.round(seconds / 60.0)
    val hours = Math.round(minutes / 60.0)

    when (seconds) {
        in 0..60 -> text = "только что"
        in 61..3600 -> text = " $minutes ${getMinuteDeclination(minutes.toInt())} назад"
        in 3601..86400 -> text = " $hours ${getHoursDeclination(hours.toInt())} назад"
        in 86401..172800 -> text = " сегодня"
        in 172801..345600 -> text = " вчера"
        else -> { text = " давно" }
    }

    println(text)
}
fun getMinuteDeclination(minutes: Int): String {
    var result = " минут"
    val value = minutes % 100
    val num = value % 10
    if((value > 10 && value < 20) || (num > 1 && num < 5)) result = " минуты"
    if(num == 1) result = " минуту"

    return result
}
fun getHoursDeclination(hours: Int): String{
    var result = " часов"
    val value = hours % 100
    val num = value % 10
    if((value > 10 && value < 20) || (num > 1 && num < 5)) result = " часа"
    if(num == 1) result = " час"

    return result
}

fun getCommission(cardType: String = "VK Pay", previousSum: Int = 0, amount: Int) {
    var result: Double = 0.0

    if (cardType == "VK Pay" && (amount > 1_500_000 || previousSum > 4_000_000)) {
        println("Вы превысили лимит по переводам")
    } else if ((cardType == "Mastercard"
                || cardType == "Maestro"
                || cardType == "Visa"
                || cardType == "МИР"
                ) && (amount > 15_000_000 || previousSum > 60_000_000))  {
        println("Вы превысили лимит по переводам")
    } else {
        when (cardType) {
            "Mastercard", "Maestro" -> {
                if (amount > 75_000) {
                    result = (amount * 0.6 / 100) + 20
                } else {
                    result = 0.0
                }
            }
            "Visa", "МИР", -> {
                val commission = (amount * 0.75 / 100)
                if (commission < 3_500) {
                    result = 3_500.0
                } else {
                    result = commission
                }
            }
            "VK Pay" ->  result = 0.0
        }
        println("Комиссия за перевод с карты $cardType: " + Math.round(result) + " коп.")
    }
}
