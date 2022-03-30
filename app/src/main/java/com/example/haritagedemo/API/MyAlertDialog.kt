package com.example.haritagedemo.API

import android.content.Context
import android.content.DialogInterface
import android.widget.Button
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog

class MyAlertDialog:AlertDialog {

    lateinit var builder: AlertDialog.Builder

    constructor(context: Context):super(context){
        init(context,null)
    }

    constructor(context: Context,@StyleRes themeResId: Int):super(context,themeResId){
        init(context,themeResId)
    }


    private fun init(mContext: Context, @StyleRes themeResId: Int?) {
        builder = AlertDialog.Builder(mContext).takeIf { themeResId == null }
            ?:AlertDialog.Builder(mContext,themeResId!!)
    }

    fun setPositiveButton(
        button: String,
        listener: DialogInterface.OnClickListener
    ):MyAlertDialog{
        builder.setPositiveButton(button,listener)
        return this
    }

    fun setPositiveButton(button: String): MyAlertDialog {
        builder.setPositiveButton(button, null)
        return this
    }

    fun setNegativeButton(
        button: String,
        listener: DialogInterface.OnClickListener
    ): MyAlertDialog {
        builder = builder.setNegativeButton(button, listener)
        return this
    }

    fun setNegativeButton(button: String): MyAlertDialog {
        builder.setNegativeButton(button, null)
        return this
    }

    fun setMTitle(title: String): MyAlertDialog {
        builder.setTitle(title)
        return this
    }

    fun setItems(
        items: Array<CharSequence>,
        listener: DialogInterface.OnClickListener
    ): MyAlertDialog {
        builder.setItems(items, listener)
        return this
    }

    fun setMessage(message: Any): MyAlertDialog {
        when (message) {
            is Int -> setMessage(message)
            is String -> setMessage(message)
            is CharSequence -> builder.setMessage(message)
            else -> throw RuntimeException("Invalid message type It should be either IntResourceId or String or CharSequences..")
        }
        return this
    }

    fun setMessage(message: String): MyAlertDialog {
        builder.setMessage(message)
        return this
    }

    fun setMessage(messageId: Int): MyAlertDialog {
        builder.setMessage(messageId)
        return this
    }
    fun setItems(items: Array<String>, listener: DialogInterface.OnClickListener): MyAlertDialog {
        builder.setItems(items, listener)
        return this
    }

    fun isCancelable(isCancelable: Boolean): MyAlertDialog {
        builder.setCancelable(isCancelable)
        return this
    }

    override fun show() {
        val dialog = builder.create()
        dialog.show()
    }
}