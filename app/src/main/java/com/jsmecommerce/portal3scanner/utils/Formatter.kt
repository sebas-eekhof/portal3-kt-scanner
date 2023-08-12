package com.jsmecommerce.portal3scanner.utils

import android.content.Context
import com.jsmecommerce.portal3scanner.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit.DAYS
import java.util.Currency
import java.util.Date

class Formatter {
    companion object {
        fun humanDate(date: String, context: Context): String = humanDate(Instant.parse(date), context)
        fun humanDate(date: Date, context: Context): String = humanDate(date.toInstant(), context)

        fun humanDate(date: Instant, context: Context): String {
            val zonedTimestamp = ZonedDateTime.ofInstant(date, ZoneId.systemDefault())
            val currentTimestamp = Instant.now()
            val zonedCurrentTimestamp = ZonedDateTime.ofInstant(currentTimestamp, ZoneId.systemDefault())

            return when(zonedTimestamp.toLocalDate().until(zonedCurrentTimestamp, DAYS)) {
                0L -> String.format(context.getString(R.string.today_at), "${String.format("%02d", zonedTimestamp.hour)}:${String.format("%02d", zonedTimestamp.minute)}")
                1L -> String.format(context.getString(R.string.yesterday_at), "${String.format("%02d", zonedTimestamp.hour)}:${String.format("%02d", zonedTimestamp.minute)}")
                else -> zonedTimestamp.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM))
            }
        }

        fun moneyFormat(amount: Double): String {
            return "€ ${DecimalFormat("###,###,##0.00").format(amount)}".replace(".", "^").replace(",", ".").replace("^", ",")
        }
    }
}