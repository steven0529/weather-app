package ph.homecredit.recruitment.util

import java.math.RoundingMode

fun convertIdsListToOpenWeatherGroupParam(idList: MutableList<String>): String {
    var ids = ""
    for (id in idList) {
        ids += "$id,"
    }
    return ids
}

fun Double.toOneDecimalPlace(): String {
    return this.toBigDecimal().setScale(1, RoundingMode.UP).toString()
}