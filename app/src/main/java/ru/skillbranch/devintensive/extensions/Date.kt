package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val tempDiff: Long = (date.time / 1000 - this.time / 1000) * 1000
    val diff = abs(tempDiff)
    var result = ""
    result += when {
        diff <= 1 * SECOND -> "только что"
        diff <= 45 * SECOND -> "несколько секунд"
        diff <= 75 * SECOND -> "минуту"
        diff <= 45 * MINUTE -> {
            "${diff / MINUTE} " + if ((diff / MINUTE) in 10..19) "минут" else {
                when ((diff / MINUTE).toInt() % 10) {
                    1 -> "минуту"
                    2, 3, 4 -> "минуты"
                    0, 5, 6, 7, 8, 9 -> "минут"
                    else -> throw IllegalStateException("invalid time")
                }
            }
        }
        diff <= 75 * MINUTE -> "час"
        diff <= 22 * HOUR -> {
            "${diff / HOUR} " + if ((diff / HOUR) in 10..19) "часов" else {
                when ((diff / HOUR).toInt() % 10) {
                    1 -> "час"
                    2, 3, 4 -> "часа"
                    0, 5, 6, 7, 8, 9 -> "часов"
                    else -> throw IllegalStateException("invalid time")
                }
            }
        }
        diff <= 26 * HOUR -> "день"
        diff <= 360 * DAY -> {
            "${diff / DAY} " + if ((diff / DAY) in 10..19) "дней" else {
                when ((diff / DAY).toInt() % 10) {
                    1 -> "день"
                    2, 3, 4 -> "дня"
                    0, 5, 6, 7, 8, 9 -> "дней"
                    else -> throw IllegalStateException("invalid time")
                }
            }
        }
        diff > 360 * DAY -> "более года"
        else -> throw IllegalStateException("invalid time")
    }
    if (!result.contains("только что")) {
        if (tempDiff >= 0) {
            result += " назад"
        } else {
            result = "через $result"
        }
    }
    if (result == "через более года") result = "более чем через год"
    return result
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        return when (this) {
            SECOND -> {
                "$value " + if (value in 10..19) "секунд" else {
                    when (value % 10) {
                        1 -> "секунду"
                        2, 3, 4 -> "секунды"
                        0, 5, 6, 7, 8, 9 -> "секунд"
                        else -> throw IllegalStateException("invalid time")
                    }
                }
            }
            MINUTE -> {
                "$value " + if (value in 10..19) "минут" else {
                    when (value % 10) {
                        1 -> "минуту"
                        2, 3, 4 -> "минуты"
                        0, 5, 6, 7, 8, 9 -> "минут"
                        else -> throw IllegalStateException("invalid time")
                    }
                }
            }
            HOUR -> {
                "$value " + if (value in 10..19) "часов" else {
                    when (value % 10) {
                        1 -> "час"
                        2, 3, 4 -> "часа"
                        0, 5, 6, 7, 8, 9 -> "часов"
                        else -> throw IllegalStateException("invalid time")
                    }
                }
            }
            DAY -> {
                "$value " + if (value in 10..19) "дней" else {
                    when (value % 10) {
                        1 -> "день"
                        2, 3, 4 -> "дня"
                        0, 5, 6, 7, 8, 9 -> "дней"
                        else -> throw IllegalStateException("invalid time")
                    }
                }
            }
        }
    }
}