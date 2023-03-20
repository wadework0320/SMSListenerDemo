package com.example.smslistenerdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage

class SmsReceiver : BroadcastReceiver() {

    private var smsListener: SmsListener? = null

    fun setSmsListener(smsListener: SmsListener) {
        this.smsListener = smsListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            intent?.extras?.apply {
                val pdus = get("pdus") as Array<*>
                val messages = arrayOfNulls<SmsMessage>(pdus.size)

                for (i in pdus.indices) {
                    messages[i] =
                        SmsMessage.createFromPdu(pdus[i] as ByteArray, getString("format"))
                }

                smsListener?.message(messages)
            }
        }
    }
}