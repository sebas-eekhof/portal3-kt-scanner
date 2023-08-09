package com.jsmecommerce.portal3scanner.models

import java.net.URLDecoder

data class Scan(private val barcodeRaw: String, val labelType: LabelType) {
    var barcode: String
    var barcodeType: BarcodeType? = null
    var barcodeSubType: BarcodeSubType? = null

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

    enum class BarcodeType {
        SHIPMENT,
        EAN
    }

    enum class BarcodeSubType {
        SHIPMENT_DHL,
        SHIPMENT_DPD,
        SHIPMENT_POSTNL,
        EAN,
        UPS
    }

    init {
        barcode = barcodeRaw.trim()
        if(labelType == LabelType.CODE39 && barcode.matches(Regex("3S[A-Z]{4}[0-9]{9}"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_POSTNL
        } else if(labelType == LabelType.CODE128 && barcode.matches(Regex("%[0-9]{5,7}[A-Z]{2}[0-9A-Z]{20}"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_DPD
        } else if(labelType == LabelType.CODE128 && barcode.matches(Regex("JVGL[0-9]{20}"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_DHL
        } else if(listOf(LabelType.EAN8, LabelType.EAN13, LabelType.EAN128).contains(labelType) && (barcode.matches(Regex("[0-9]{12,13}")))) {
            barcodeType = BarcodeType.EAN
            barcodeSubType = if (barcode.length == 12) BarcodeSubType.UPS else BarcodeSubType.EAN
        }
    }

    fun getShipmentBarcode(): String {
        if(barcodeSubType == BarcodeSubType.SHIPMENT_DPD) {
            val matchRes = Regex("%[0-9]{5,7}[A-Z]{2}([0-9A-Z]{14})").find(barcode)
            if(matchRes != null && matchRes.groupValues.count() >= 2)
                return matchRes.groupValues[1]
            return barcode
        }
        return barcode
    }
}