package com.bsoft.linkqueue.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast

class Extensions {

    companion object {

        fun Activity.toast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}