package com.jsmecommerce.portal3scanner.models

data class Scan(val barcode: String, val labelType: LabelType) {
    enum class LabelType {
        EAN8,
        EAN13,
        EAN128,
        CODE128,
        CODE39,
        QRCODE,
        UPCA,
        PDF417,
        GS1_DATABAR,
        UPCE0,
        DATAMATRIX,
        AZTEC,
        MAXICODE,
        UNDEFINED
    }

    fun isEAN(): Boolean = listOf(
        LabelType.EAN8,
        LabelType.EAN13,
        LabelType.EAN128
    ).contains(labelType) && (barcode.matches(Regex("[0-9]{12,13}")))
    fun isPostNL(): Boolean = labelType == LabelType.CODE39 && barcode.matches(Regex("3S[A-Z]{4}[0-9]{9}"))
    fun isDPD(): Boolean = labelType == LabelType.CODE128 && barcode.matches(Regex("%[0-9]{5}[A-Z]{2}[0-9]{20}"))
    fun isDHL(): Boolean = labelType == LabelType.CODE128 && barcode.matches(Regex("JVGL[0-9]{20}"))
}