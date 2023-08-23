package com.jsmecommerce.portal3scanner.models

import android.content.Intent
import com.jsmecommerce.portal3scanner.utils.Validator
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
        UNDEFINED;

        companion object {
            fun fromDataWedgeString(string: String): LabelType = when(string) {
                "LABEL-TYPE-EAN13" -> Scan.LabelType.EAN13
                "LABEL-TYPE-QRCODE" -> Scan.LabelType.QRCODE
                "LABEL-TYPE-EAN8" -> Scan.LabelType.EAN8
                "LABEL-TYPE-PDF417" -> Scan.LabelType.PDF417
                "LABEL-TYPE-GS1-DATABAR" -> Scan.LabelType.GS1_DATABAR
                "LABEL-TYPE-EAN128" -> Scan.LabelType.EAN128
                "LABEL-TYPE-CODE128" -> Scan.LabelType.CODE128
                "LABEL-TYPE-CODE39" -> Scan.LabelType.CODE39
                "LABEL-TYPE-UPCA" -> Scan.LabelType.UPCA
                "LABEL-TYPE-UPCE0" -> Scan.LabelType.UPCE0
                "LABEL-TYPE-DATAMATRIX" -> Scan.LabelType.DATAMATRIX
                "LABEL-TYPE-AZTEC" -> Scan.LabelType.AZTEC
                "LABEL-TYPE-MAXICODE" -> Scan.LabelType.DATAMATRIX
                else -> Scan.LabelType.UNDEFINED
            }
        }
    }

    enum class BarcodeType {
        SHIPMENT,
        EAN,
        PRODUCT,
        IDENTIFICATION
    }

    enum class BarcodeSubType {
        SHIPMENT_DHL,
        SHIPMENT_DPD,
        SHIPMENT_POSTNL,
        EAN,
        UPS,
        PRODUCT_ID,
        ID_CARD,
        DRIVERS_LICENSE
    }

    init {
        barcode = barcodeRaw.trim()
        if(listOf(LabelType.CODE39, LabelType.CODE128).contains(labelType) && barcode.matches(Regex("^3S[A-Z]{4}[0-9]{7,9}$"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_POSTNL
        } else if(labelType == LabelType.CODE128 && barcode.matches(Regex("^%[0-9]{5,7}[A-Z]{2}[0-9A-Z]{20}$"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_DPD
        } else if(labelType == LabelType.CODE128 && barcode.matches(Regex("^JVGL[0-9]{16,20}$"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_DHL
        } else if(labelType == LabelType.PDF417 && barcode.startsWith("UNH") && barcode.contains(Regex("JVGL[0-9]{16,20}"))) {
            barcodeType = BarcodeType.SHIPMENT
            barcodeSubType = BarcodeSubType.SHIPMENT_DHL
        } else if(listOf(LabelType.EAN8, LabelType.EAN13, LabelType.EAN128, LabelType.CODE128).contains(labelType) && (barcode.matches(Regex("^[0-9]{12,13}$")))) {
            barcodeType = BarcodeType.EAN
            barcodeSubType = if (barcode.length == 12) BarcodeSubType.UPS else BarcodeSubType.EAN
        } else if(labelType == LabelType.CODE128 && barcode.matches(Regex("^[0-9]{1,6}$"))) {
            barcodeType = BarcodeType.PRODUCT
            barcodeSubType = BarcodeSubType.PRODUCT_ID
        } else if(labelType == LabelType.QRCODE && barcode.length == 9 && Validator(barcode).isValidBSN()) {
            barcodeType = BarcodeType.IDENTIFICATION
            barcodeSubType = BarcodeSubType.ID_CARD
        } else if(labelType == LabelType.QRCODE && barcode.matches(Regex("^D1NLD[0-9A-Z]{25}"))) {
            barcodeType = BarcodeType.IDENTIFICATION
            barcodeSubType = BarcodeSubType.DRIVERS_LICENSE
        }
    }

    fun getShipmentBarcode(): String {
        if(barcodeSubType == BarcodeSubType.SHIPMENT_DPD) {
            val matchRes = Regex("%[0-9]{5,7}[A-Z]{2}([0-9A-Z]{14})").find(barcode)
            if(matchRes != null && matchRes.groupValues.count() >= 2)
                return matchRes.groupValues[1]
            return barcode
        } else if(barcodeSubType == BarcodeSubType.SHIPMENT_DHL && labelType == LabelType.PDF417) {
            val matchRes = Regex("(JVGL[0-9]{16,20})").find(barcode)
            if(matchRes != null && matchRes.groupValues.count() >= 2)
                return matchRes.groupValues[1]
            return barcode
        }
        return barcode
    }

    companion object {
        fun fromIntent(intent: Intent): Scan? {
            if(
                listOf(
                    "com.symbol.datawedge.label_type",
                    "com.symbol.datawedge.scanner_identifier",
                    "com.symbol.datawedge.data_string"
                ).any { !intent.hasExtra(it) }
            ) return null
            return Scan(
                barcodeRaw = intent.getStringExtra("com.symbol.datawedge.data_string")!!,
                labelType = LabelType.fromDataWedgeString(intent.getStringExtra("com.symbol.datawedge.label_type")!!)
            )
        }
    }
}