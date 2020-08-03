package com.netmedsassignment.data.api

import com.netmedsassignment.R
import com.netmedsassignment.app.MyApplication
import java.io.IOException

// Exception defined class used when No internet is there
class NoConnectivityException : IOException() {

    override val message: String?
        get() = MyApplication.mInstance.getString(R.string.no_internet_message)

}