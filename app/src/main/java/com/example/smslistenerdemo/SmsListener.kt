package com.example.smslistenerdemo

import android.telephony.SmsMessage

interface SmsListener {

    fun message(messages: Array<SmsMessage?>)

}