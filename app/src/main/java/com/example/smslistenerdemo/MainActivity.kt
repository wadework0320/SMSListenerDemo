package com.example.smslistenerdemo

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.smslistenerdemo.databinding.ActivityMainBinding

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val smsReceiver = SmsReceiver()
    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.textView.text = "已授權"
            } else {
                binding.textView.text = "未授權"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(smsReceiver, intentFilter)
        smsReceiver.setSmsListener(object : SmsListener {
            override fun message(messages: Array<SmsMessage?>) {
                val messageBody = messages[0]?.messageBody
                val sender = messages[0]?.originatingAddress
                binding.textView.text = "${sender}\n${messageBody}"
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(smsReceiver)
    }

    fun listener(view: View) {
        permission.launch(android.Manifest.permission.RECEIVE_SMS)
    }

}