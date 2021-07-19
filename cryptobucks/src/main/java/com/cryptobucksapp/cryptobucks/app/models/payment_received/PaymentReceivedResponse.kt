package com.cryptobucksapp.cryptobucks.app.models.payment_received


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentReceivedResponse(

    @Json(name = "fiatRate")
    val fiatRate: Double? = null,

    @Json(name = "cryptoAmount")
    val cryptoAmount: Double? = null,

    @Json(name = "cryptoFee")
    val cryptoFee: Double? = null,

    @Json(name = "fiatAmount")
    val fiatAmount: Double? = null,

    @Json(name = "invoice")
    val invoice: String? = null,

    @Json(name = "invoiceStatus")
    val invoiceStatus: String? = null,

    @Json(name = "hash")
    val hash: String? = null,

    @Json(name = "crypto")
    val crypto: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(fiatRate)
        parcel.writeValue(cryptoAmount)
        parcel.writeValue(cryptoFee)
        parcel.writeValue(fiatAmount)
        parcel.writeString(invoice)
        parcel.writeString(invoiceStatus)
        parcel.writeString(hash)
        parcel.writeString(crypto)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<PaymentReceivedResponse> {
        override fun createFromParcel(parcel: Parcel): PaymentReceivedResponse {
            return PaymentReceivedResponse(parcel)
        }

        override fun newArray(size: Int): Array<PaymentReceivedResponse?> {
            return arrayOfNulls(size)
        }
    }
}