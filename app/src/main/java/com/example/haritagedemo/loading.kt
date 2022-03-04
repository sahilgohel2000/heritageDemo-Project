package com.example.haritagedemo

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.view.View

class loading(
    val myActivity: Activity
) {

    val activity = myActivity
    var dialog:AlertDialog?=null

    fun loadingAlertDialog() {
        val builder = AlertDialog.Builder(activity)

        val layoutInflater=activity.layoutInflater
        builder.setView(View.inflate(activity,R.layout.custom_dialog,null))
        builder.setCancelable(false)

        dialog=builder.create()
        dialog!!.show()
    }

    fun dismissDialog(){
        dialog!!.dismiss()
    }
}