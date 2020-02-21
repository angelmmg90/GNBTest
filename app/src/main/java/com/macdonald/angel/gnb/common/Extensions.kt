package com.macdonald.angel.gnb.common

import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty


fun Fragment.messageToShow(messageToShow: String, isError: Boolean){
    var toast = if(isError){
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

