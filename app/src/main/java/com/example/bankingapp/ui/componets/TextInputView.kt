package com.example.bankingapp.ui.componets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.bankingapp.R
import com.example.bankingapp.databinding.ViewTextInputBinding
import androidx.core.content.withStyledAttributes

class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTextInputBinding

    // Input type constants for easy access
    enum class InputType {
        TEXT,
        PASSWORD,
        EMAIL,
        PHONE,
        NUMBER,
        MULTILINE
    }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewTextInputBinding.inflate(inflater, this, true)

        orientation = VERTICAL
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            context.withStyledAttributes(
                it,
                R.styleable.TextInputView,
                0,
                0
            ) {

                // Set label text
                val labelText = getString(R.styleable.TextInputView_labelText)
                setLabelText(labelText ?: "")

                // Set hint text
                val hintText = getString(R.styleable.TextInputView_hintText)
                setHintText(hintText ?: "")

                // Set input type using the new inputTypeAttr
                // Map enum int to InputType enum
                val inputTypeAttr = getInt(R.styleable.TextInputView_inputTypeAttr, 0)
                val inputTypeEnum = when(inputTypeAttr) {
                    0 -> InputType.TEXT
                    1 -> InputType.PASSWORD
                    2 -> InputType.EMAIL
                    3 -> InputType.PHONE
                    4 -> InputType.NUMBER
                    5 -> InputType.MULTILINE
                    else -> InputType.TEXT
                }
                setInputType(inputTypeEnum)

                // Set end drawable if specified
                val drawableEndResId = getResourceId(R.styleable.TextInputView_drawableEnd, 0)
                if (drawableEndResId != 0) {
                    setEndDrawable(drawableEndResId)
                }

            }
        }
    }

    // Method 1: Set input type using enum (recommended)
    fun setInputType(type: InputType) {
        when (type) {
            InputType.TEXT -> binding.textInputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT
            InputType.PASSWORD -> {
                binding.textInputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.textInputEditText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            }
            InputType.EMAIL -> binding.textInputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            InputType.PHONE -> binding.textInputEditText.inputType = android.text.InputType.TYPE_CLASS_PHONE
            InputType.NUMBER -> binding.textInputEditText.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            InputType.MULTILINE -> {
                binding.textInputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
                binding.textInputEditText.maxLines = 3
            }
        }
    }



    fun setEndDrawable(resId: Int) {
        binding.textInputEditText.setCompoundDrawablesWithIntrinsicBounds(
            0, // left
            0, // top
            resId, // right/end
            0  // bottom
        )
    }

    // Method 3: Convenience methods for common input types
    fun setAsPasswordField() {
        setInputType(InputType.PASSWORD)
    }

    fun setAsEmailField() {
        setInputType(InputType.EMAIL)
    }

    fun setAsPhoneField() {
        setInputType(InputType.PHONE)
    }

    fun setAsNumericField() {
        setInputType(InputType.NUMBER)
    }

    fun setAsMultilineField(lines: Int = 3) {
        setInputType(InputType.MULTILINE)
        binding.textInputEditText.maxLines = lines
    }

    // Other methods remain the same
    fun setLabelText(text: String) {
        binding.textInputLabel.text = text
    }

    fun setHintText(text: String) {
        binding.textInputEditText.hint = text
    }

    fun getText(): String {
        return binding.textInputEditText.text?.toString()?.trim() ?: ""
    }

    fun setText(text: String) {
        binding.textInputEditText.setText(text)
    }

    fun setError(error: String?) {
        binding.textInputEditText.error = error
    }

    fun clearError() {
        binding.textInputEditText.error = null
    }

    fun setOnTextChangeListener(listener: (String) -> Unit) {
        binding.textInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                listener(getText())
            }
        }
    }
}