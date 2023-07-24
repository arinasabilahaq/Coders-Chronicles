package com.arina.mystoryapp.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.arina.mystoryapp.R
import com.arina.mystoryapp.util.isValidPassword
import com.google.android.material.textfield.TextInputEditText


class PasswordTField : TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (isValidPassword(text.toString())) {
                    this@PasswordTField.error = null
                } else {
                    this@PasswordTField.error = context.getString(R.string.pass_length)
                }
            }
        })
    }
}
