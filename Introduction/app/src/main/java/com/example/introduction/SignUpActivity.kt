package com.example.introduction

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.introduction.databinding.ActivitySignUpBinding
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class SignUpActivity : AppCompatActivity() {
    companion object {
        fun newIntent(
            context: Context,
        ): Intent = Intent(context, SignUpActivity()::class.java)
    }

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private val viewModel: SignUpViewModel by viewModels()

    private val editTexts
        get() = with(binding) {
            listOf(
                nameET,
                emailET,
                pwdET,
                pwdCheckET
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() {
        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                editText.checkValidElements()
                viewModel.isConfirmButtonEnable()
            }

            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus.not()) {
                    editText.checkValidElements()
                    //viewModel.isConfirmButtonEnable()
                }
            }
        }
        binding.signupBtn.setOnClickListener(){
            val name = binding.nameET.text.toString()
            val id = binding.emailET.text.toString()
            val password = binding.pwdET.text.toString()

            val member = SignUpMember(
                name,
                id,
                password
            )
            viewModel.onClick(member)
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("pwd", password)
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        }
    }

    private fun initViewModel() = with(viewModel) {

        errorUiState.observe(this@SignUpActivity) { uiState ->
            with(binding) {
                // 이름
                nameWarningTV.visibility = when (uiState.name) {
                    SignUpValidUiState.Name -> View.VISIBLE
                    else -> View.GONE
                }

                // 이메일
                emailWarningTV.setText(
                    when (uiState.emailId) {
                        SignUpValidUiState.EmailBlank -> R.string.sign_up_email_error_blank
                        SignUpValidUiState.Emailvalid -> R.string.sign_up_email_error
                        else -> R.string.sign_up_pass
                    }
                )
                emailWarningTV.visibility = when (uiState.emailId) {
                    SignUpValidUiState.EmailBlank -> View.VISIBLE
                    SignUpValidUiState.Emailvalid -> View.VISIBLE
                    else -> View.GONE
                }

                // 비밀번호 입력
                pwdWarningTV.setText(
                    when (uiState.passwordInput) {
                        SignUpValidUiState.PasswordInputLength -> R.string.sign_up_password_error_length
                        SignUpValidUiState.PasswordInputSpecialCharacters -> R.string.sign_up_password_error_special
                        SignUpValidUiState.PasswordInputUpperCase -> R.string.sign_up_password_error_upper
                        else -> R.string.sign_up_pass
                    }
                )
                pwdWarningTV.visibility = when (uiState.passwordInput) {
                    SignUpValidUiState.PasswordInputLength -> View.VISIBLE
                    SignUpValidUiState.PasswordInputSpecialCharacters -> View.VISIBLE
                    SignUpValidUiState.PasswordInputUpperCase -> View.VISIBLE
                    else -> View.GONE
                }

                // 비밀번호 확인
                pwdCheckWarningTV.visibility = when (uiState.passwordConfirm) {
                    SignUpValidUiState.PasswordConfirm -> View.VISIBLE
                    else -> View.GONE
                }

                // 버튼
                signupBtn.isEnabled = uiState.enabled
                Log.d("TAG", "uiState.enabled: "+uiState.enabled)
            }
        }

        event.observe(this@SignUpActivity) { event ->
            when (event) {
                is SignUpEvent.SignUpSuccess -> {
                    Toast.makeText(
                        this@SignUpActivity,
                        event.member.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // 각 항목의 유효성 검사를 처리
    private fun EditText.checkValidElements() = with(binding) {
        when (this@checkValidElements) {
            nameET -> viewModel.checkValidName(nameET.text.toString())
            emailET -> viewModel.checkValidEmail(emailET.text.toString())
            pwdET -> viewModel.checkValidPasswordInput(pwdET.text.toString())
            pwdCheckET -> viewModel.checkValidPasswordConfirm(
                pwdET.text.toString(),
                pwdCheckET.text.toString(),
            )

            else -> Unit
        }
    }
}