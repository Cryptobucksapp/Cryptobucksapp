package com.cryptobucksapp.cryptobucks.ui.mvvm

import android.annotation.SuppressLint
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.CurrenciesItem
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceRequest
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceResponse
import com.cryptobucksapp.cryptobucks.app.models.payment_received.PaymentReceivedResponse
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.base.BaseViewModel
import com.cryptobucksapp.cryptobucks.utils.events.EventLiveData
import com.cryptobucksapp.cryptobucks.utils.manager.SessionManager
import com.cryptobucksapp.cryptobucks.utils.views.MerchantConverter
import com.cryptobucksapp.cryptobucks.utils.views.UtilityTextWatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var invoiceRepository: InvoiceRepository

    lateinit var id: String

    //    Merchant Sale

    private val invoiceRequest = InvoiceRequest()

    val mutableVisibilityButton = MutableLiveData<Int>(View.INVISIBLE)

    private val _contractPersonValue = MutableLiveData<String>("")
    val contractPersonValue: LiveData<String> = _contractPersonValue

    private val _emailValue = MutableLiveData<String>("")
    val emailValue: LiveData<String> = _emailValue

    private val _phoneNumberValue = MutableLiveData<String>("")
    val phoneNumberValue: LiveData<String> = _phoneNumberValue

    private val _noteValue = MutableLiveData<String>("")
    val noteValue: LiveData<String> = _noteValue

    private var amount = ""
    private var lastEditText = false

    // Invoice
    val mutableVisibilityAddress = MutableLiveData<Int>(View.GONE)
    val mutableVisibilityCrypto = MutableLiveData<Int>(View.VISIBLE)

    var mutableInvoiceDate = MutableLiveData<String>("20 / 02 / 2020")

    var mutableInvoiceName = MutableLiveData<String>("John Williams")
    var mutableInvoiceEmail = MutableLiveData<String>("John_ment@gmail.com")
    var mutableInvoicePhone = MutableLiveData<String>("+1 954 555 1221")
    var mutableInvoiceNote = MutableLiveData<String>("Test Run")
    var mutableInvoiceAmount = MutableLiveData<String>("198,00 USD")
    var mutableInvoiceTaxAmount = MutableLiveData<String>("")
    var mutableInvoiceTipAmount = MutableLiveData<String>("")
    var mutableInvoiceFee = MutableLiveData<String>()
    var mutableInvoiceTotalAmount = MutableLiveData<String>("200,00 USD")

    var mutableCrypto = MutableLiveData<String>()
    var mutableTitleAddress = MutableLiveData<String>()
    var mutableCryptoAddress = MutableLiveData<String>()
    var mutableWebLink = MutableLiveData<String>()
    var mutableTransactionIDWallet = MutableLiveData<String>()

    private val _criptosItem =
        MutableLiveData<InvoiceResponse?>(null)
    val criptosItem: LiveData<InvoiceResponse?> = _criptosItem

    private val _qrData =
        MutableLiveData<String>(null)
    val qrData: LiveData<String> = _qrData

    fun contractPersonTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            _contractPersonValue.postValue(it.toString())
            lastEditText = true
            if (lastEditText && amount.isNotEmpty()) {
                when {
                    emailValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    phoneNumberValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    noteValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    else -> mutableVisibilityButton.postValue(View.VISIBLE)
                }
            }
        } else {
            mutableVisibilityButton.postValue(View.INVISIBLE)
            lastEditText = false
        }
    }

    fun emailTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            _emailValue.postValue(it.toString())
            lastEditText = true
            if (lastEditText && amount.isNotEmpty()) {
                when {
                    contractPersonValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    phoneNumberValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    noteValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    else -> mutableVisibilityButton.postValue(View.VISIBLE)
                }
            }
        } else {
            mutableVisibilityButton.postValue(View.INVISIBLE)
            lastEditText = false
        }
    }

    fun phoneNumberTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            _phoneNumberValue.postValue(it.toString())
            lastEditText = true
            if (lastEditText && amount.isNotEmpty()) {
                when {
                    contractPersonValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    emailValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    noteValue.value.isNullOrEmpty() -> return@UtilityTextWatcher
                    else -> mutableVisibilityButton.postValue(View.VISIBLE)
                }
            }
        } else {
            mutableVisibilityButton.postValue(View.INVISIBLE)
            lastEditText = false
        }
    }

    fun noteTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            _noteValue.postValue(it.toString())
        }
    }

    fun setVisible() {
        mutableVisibilityButton.postValue(View.INVISIBLE)
    }

    fun setAmount(amount: String) {
        this.amount = amount
        when {
            contractPersonValue.value.isNullOrEmpty() -> {
                mutableVisibilityButton.postValue(View.INVISIBLE)
                return
            }
            emailValue.value.isNullOrEmpty() -> {
                mutableVisibilityButton.postValue(View.INVISIBLE)
                return
            }
            phoneNumberValue.value.isNullOrEmpty() -> {
                mutableVisibilityButton.postValue(View.INVISIBLE)
                return
            }
            else -> {
                mutableVisibilityButton.postValue(View.VISIBLE)
            }
        }
    }

    fun sendMerchant() {

        if (contractPersonValue.value.isNullOrEmpty()) {
            mutableErrorMessage.value =
                EventLiveData(Commons.getString(R.string.text_required_contract_person))
            return
        }

        if (!Commons.isValidEmail(emailValue.value.toString())) {
            mutableErrorMessage.value =
                EventLiveData(Commons.getString(R.string.text_required_email))
            return
        }

        if (phoneNumberValue.value.isNullOrEmpty()) {
            mutableErrorMessage.value =
                EventLiveData(Commons.getString(R.string.text_required_phone_number))
            return
        }

        if (amount.isEmpty()) {
            mutableErrorMessage.value =
                EventLiveData(Commons.getString(R.string.text_required_amount))
            return
        }

        viewModelScope.launch {
            mutableProgress.value = View.VISIBLE

            invoiceRequest.name = contractPersonValue.value
            invoiceRequest.email = emailValue.value
            invoiceRequest.phone = phoneNumberValue.value
            invoiceRequest.description = noteValue.value
            invoiceRequest.amount = amount.substring(1).toDouble()

            mutableProgress.postValue(View.GONE)

            val response = invoiceRepository.invoice(
                this@MainActivityViewModel,
                invoiceRequest
            )

            if (response != null) {
                mutableInvoiceName.value = response.data!!.invoice!!.name!!
                mutableInvoiceDate.value =  MerchantConverter.getFormatDate(
                    response.data.invoice!!.createdAt!!
                )
                mutableInvoicePhone.value = response.data.invoice.phone!!
                mutableInvoiceEmail.value = response.data.invoice.email!!
                mutableInvoiceNote.value = response.data.invoice.description!!
                mutableInvoiceAmount.value = response.data.invoice.amount.toString()
                mutableInvoiceFee.value = response.data.invoice.networkFee.toString()
                mutableInvoiceTotalAmount.value = response.data.invoice.totalAmount.toString()

                _criptosItem.postValue(response)

                SessionManager.getInstance().setResponse(response)
                mutableSuccessMessage.postValue(EventLiveData(response.data.invoice.id!!))
            }
        }
    }

    val mutableVisibilityTag = MutableLiveData<Int>(View.GONE)

    fun getAddressCrypto(currency: CurrenciesItem, id: String) {
        viewModelScope.launch {
            mutableProgress.postValue(View.VISIBLE)

            val addressResponse = invoiceRepository.addressCrypto(
                this@MainActivityViewModel, id, currency.currency!!)

            if (addressResponse != null) {
                mutableVisibilityAddress.postValue(View.VISIBLE)
                mutableVisibilityCrypto.postValue(View.GONE)
                mutableProgress.postValue(View.GONE)
                mutableCrypto.value = currency.imageEnable!!
                when (currency.currency) {
                    "xrp", "txrp" -> {
                        mutableVisibilityTag.postValue(View.VISIBLE)
                        val newAddress = addressResponse.data!!.address!!.split("?dt=")
                        mutableCryptoAddress.value = newAddress[0]
                        mutableWebLink.value = newAddress[1]
                    }
                    else -> {
                        mutableVisibilityTag.postValue(View.GONE)
                        mutableCryptoAddress.value = addressResponse.data!!.address!!
                    }
                }

                mutableTitleAddress.value = "${Commons.camelCase(currency.name!!)} Address"
                mutableTransactionIDWallet.value = MerchantConverter.getUid(id)
                _qrData.postValue(addressResponse.data.qrData!!)
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun clearStatusAll() {
        _contractPersonValue.postValue("")
        _emailValue.postValue("")
        _phoneNumberValue.postValue("")
        _noteValue.postValue("")
        mutableVisibilityButton.postValue(View.INVISIBLE)
    }

    // Payment Received

    var mutableStatus = MutableLiveData<String>()
    var mutableID = MutableLiveData<String>()
    var mutableSaleAmount = MutableLiveData<String>()
    var mutableReceivedCrypto = MutableLiveData<String>()
    var mutableSettledAmount = MutableLiveData<String>()
    var mutableTransactionID = MutableLiveData<String>()

    fun merchantActivityDetails(body: PaymentReceivedResponse) {
        mutableStatus.value = body.invoiceStatus!!
        mutableID.value = body.invoice!!
        mutableSaleAmount.value = body.fiatAmount!!.toString()
        mutableReceivedCrypto.value = "${body.cryptoAmount!!} ${body.crypto}"
        mutableSettledAmount.value = "${body.fiatRate} USD"
        mutableTransactionID.value = body.hash!!
    }
}