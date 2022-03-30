package com.example.haritagedemo.API

abstract class AlertMessageListener:OnAlertMessageListener {

    override fun onPositiveButtonClick() {

    }

    override fun onNegativeButtonClick() {

    }
}

interface OnAlertMessageListener {

    fun onPositiveButtonClick()
    fun onNegativeButtonClick()

}
