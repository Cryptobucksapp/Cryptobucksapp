package com.cryptobucksapp.cryptobucks.utils.views

import android.util.Log
import androidx.core.content.ContextCompat
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.app.AliantPaymentsApp
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.enums.Filter
import java.math.MathContext
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

object MerchantConverter {

    fun getTextCamelCase(status: String) = Commons.camelCase(status)

    fun getTextCamelCaseCrypto(status: String) : String {

        return if (status.contains("_")) {
            "${Commons.camelCase(genericSplit(status, "_")[0])} ${Commons.camelCase(genericSplit(status, "_")[1])}"
        } else {
            Commons.camelCase(status)
        }
    }

    fun getTextColorStatus(status: String): Int {

        var color = ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorAccent)

        when (status) {
            Filter.CREATED.value,
            Filter.SENT.value,
            Filter.OPEN.value,
            Filter.REFUND.value -> color = ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorBrightOrange)
            Filter.VOID.value  -> color = ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorSunsetOrange)
            Filter.PROCESSING.value,
            Filter.TRADING.value,
            Filter.PENDING_SETTLEMENT.value,
            Filter.PROCESSING_SETTLEMENT.value,
            Filter.APPROVED.value -> color = ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorTurquoiseBlue)

            Filter.COMPLETED.value -> color = ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorGreen)

            Filter.SETTLEMENT_FAILED.value,
            Filter.EXCHANGE_FAILED.value,
            Filter.TRADING_FAILED.value ,
            Filter.DELETED.value,
            Filter.EXPIRED.value,
            Filter.DECLINED.value -> color = ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorRed)
        }

        return color
    }

    fun getTextUid(uid: String) = "ID: $uid"

    fun getTextUidSingle(uid: String): String {
        val space = "  ·   "
        val newId = uid.split("-")
        return "$space ID: ${newId[0]}"
    }

    fun getUid(uid: String): String {
        val newId = uid.split("-")
        return "Transaction ID: ${newId[0]}"
    }

    fun getFormatDate(date: String): String {

        val dayOfMonth = Commons.getDayOfMonth(Commons.parseStringDate(date))
        val month =
            Commons.getMonthString(Commons.parseStringDate(date), AliantPaymentsApp.appContext)
        val hour = Commons.getHour(date)
        return "$month $dayOfMonth  ·  $hour"
    }

    fun getFormatDateNormal(date: String): String {
        val day = Commons.getDayOfMonth(Commons.parseStringDate(date))
        var month = Commons.getMonth(Commons.parseStringDate(date))
        val year = Commons.getYear(Commons.parseStringDate(date))

        if (month.toInt() < 10) {
            month = "0$month"
        }
        return "$day / $month / $year"
    }

    fun getFormatAmount(amount: String): String {
        var newAmount = amount
        val listAmount = newAmount.split(".")
        if (listAmount[1].length == 1) {
            newAmount = "${newAmount}0"
        }

        return "$ $newAmount"
    }

    fun getFormatAmountInvoice(amount: String): String {
        return if (genericSplit(amount, ".")[1].length == 1)
            "${amount}0 USD"
        else
            "$amount USD"
    }

    fun getFormatAmountSale(amount: String): String {

        return if (!amount.contains(".")) {
            "$$amount.00"
        } else {
            if (genericSplit(amount, ".")[1].length == 1) {
                "$${amount}0"
            }
            else {
                "$${amount}"
            }
        }
    }

    fun getFormatAmountSaleHome(amount: String, tax: String, tip: String, fee: String): String {

        val total = (amount.toDouble() + tax.toDouble() + tip.toDouble() + fee.toDouble().toBigDecimal().
        setScale(2, RoundingMode.UP).toDouble()).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

        return if (!total.toString().contains(".")) {
            "$${total}.00"
        } else {
            if (genericSplit(total.toString(), ".")[1].length == 1) {
                "$${total}0"
            }
            else {
                "$${total}"
            }
        }
    }

    fun getFormatDateRefund(date: String?) : Boolean {

        val currentTime: Date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val dateTime = formatter.format(currentTime)
        val startDate: Date = formatter.parse(date)
        val endDate: Date = formatter.parse(dateTime)
        val different = endDate.time - startDate.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli: Long = minutesInMilli * 60
        val result = different.toDouble() / hoursInMilli.toDouble()

        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = startDate
        cal2.time = endDate

        Log.e("----->", startDate.toString())
        Log.e("----->", endDate.toString())

        return cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR] &&
                cal1[Calendar.YEAR] == cal2[Calendar.YEAR]

    }

    fun genericSplit(string: String, delimiter: String) = string.split(delimiter)
}