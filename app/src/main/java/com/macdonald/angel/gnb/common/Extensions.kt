package com.macdonald.angel.gnb.common

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty
import java.math.RoundingMode
import java.text.DecimalFormat


fun Fragment.messageToShow(messageToShow: String, isError: Boolean){
    val toast = if(isError){
        Toasty.error(
            context!!,
            messageToShow,
            Toast.LENGTH_LONG,
            true
        )
    }else{
        Toasty.info(
            context!!,
            messageToShow,
            Toast.LENGTH_LONG,
            true
        )
    }
    toast.show()
}

fun Double.round(): String {
    val df = DecimalFormat("#,##0.00")
    df.roundingMode = RoundingMode.HALF_EVEN
    return df.format(this)
}

fun ProgressBar.visible(){
    this.visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    this.visibility = View.GONE
}

