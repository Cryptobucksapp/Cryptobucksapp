package com.cryptobucksapp.cryptobucks.utils.enums

enum class Filter(val value: String) {
    CREATED("created"),
    SENT("sent"),
    OPEN("open"),
    PROCESSING("processing"),
    TRADING("trading"),
    PENDING_SETTLEMENT("pending_settlement"),
    PROCESSING_SETTLEMENT("processing_settlement"),
    COMPLETED("completed"),
    EXCHANGE_FAILED("exchange_failed"),
    TRADING_FAILED("trading_failed"),
    SETTLEMENT_FAILED("settlement_failed"),
    DELETED("deleted"),
    EXPIRED("expired"),
    VOID("VOID"),
    REFUND("REFUND"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED")

}