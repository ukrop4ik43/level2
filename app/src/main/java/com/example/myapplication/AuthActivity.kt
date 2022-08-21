package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.example.myapplication.Constants.LETTERS
import com.example.myapplication.Constants.NUMBERS
import com.example.myapplication.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val view = binding.root
        setContentView(view)
        emailFocusListener()
        passFocusListener()
        setOnClickListener()
    pressedEnter()
    }

    private fun pressedEnter() {
        binding.PassTextEdit.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                sendMessage()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setOnClickListener() {
        binding.buttonRegister.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val editText = findViewById<EditText>(R.id.EmailEditText)
        val emaill = editText.text.toString()
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, emaill)
        }
        startActivity(intent)
        finish()
    }

    private fun emailFocusListener() {
        binding.run{
            EmailEditText.setOnFocusChangeListener { _, focused ->
                if (!focused) {
                    EmailContainer.helperText = validEmail()
                }
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.EmailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return getString(R.string.invalid_email_address)
        }
        return null
    }




    private fun passFocusListener() {
        binding.PassTextEdit.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.PassContainer.helperText = validPass()
            }
        }
    }

    private fun validPass(): String? {
        val passText = binding.PassTextEdit.text.toString()
        if (passText.length < 8) {
            return "minimum 8 character required"
        }
        if (!passText.matches(NUMBERS.toRegex())) {
            return "minimum 1 number required"
        }
        if (!passText.matches(LETTERS.toRegex())) {
            return "minimum 1 lower-case letter required"
        }
        return null
    }
}