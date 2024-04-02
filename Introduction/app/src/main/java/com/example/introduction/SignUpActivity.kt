package com.example.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider

class SignUpActivity : AppCompatActivity() {
    // var은 lateinit
    private lateinit var viewModel: SignUpViewModel
    // val은 lazy
    private val nameEt: EditText by lazy {
        findViewById(R.id.name_ET)
    }
    private val idEt: EditText by lazy {
        findViewById(R.id.id_ET)
    }
    private val pwdEt: EditText by lazy {
        findViewById(R.id.pwd_ET)
    }
    private val pwdcheckEt: EditText by lazy {
        findViewById(R.id.pwd_check_ET)
    }
    private val pwdWarningTv: TextView by lazy {
        findViewById(R.id.pwd_warning_TV)
    }
    private val pwdCheckWarningTv: TextView by lazy {
        findViewById(R.id.pwd_check_warning_TV)
    }
    private val signupBtn: Button by lazy {
        findViewById(R.id.signup_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // ViewModelProvider를 통해 ViewModel 가져오기
        // LifeCycle을 가진 owner를 넣는다(this==현재 Activity)
        // get()에 가져오고 싶은 ViewModel클래스를 넣으면 완료
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.isPasswordValid.observe(this) { isPasswordValid ->
            pwdWarningTv.visibility = if (isPasswordValid) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        viewModel.isPasswordMatch.observe(this) { isPasswordMatch ->
            pwdCheckWarningTv.visibility = if (isPasswordMatch) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        // EditText들의 포커스가 변경될 때 ViewModel에 값을 업데이트
        val editTextList = listOf(nameEt, idEt, pwdEt, pwdcheckEt)
        editTextList.forEach { editText ->
            editText.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) { // 포커스를 잃은 경우에만 처리
                    val text = (view as EditText).text.toString()
                    when (view) {
                        nameEt -> viewModel.setName(text)
                        idEt -> viewModel.setId(text)
                        pwdEt -> viewModel.setPassword(text)
                        pwdcheckEt -> viewModel.setPasswordCheck(text)
                    }
                }
            }
        }
        // EditText들의 텍스트가 변경될 때마다 ViewModel에 값을 업데이트
        /*editTextList.forEach { editText ->
            editText.addTextChangedListener {
                when (editText) {
                    nameEt -> viewModel.setName(it.toString())
                    idEt -> viewModel.setId(it.toString())
                    pwdEt -> viewModel.setPassword(it.toString())
                    pwdcheckEt -> viewModel.setPasswordCheck(it.toString())
                }
            }
        }*/

        signupBtn.setOnClickListener {
            signUpButtonClick()
        }
    }

    private fun signUpButtonClick() {
        val name = nameEt.text.toString()
        val id = idEt.text.toString()
        val pwd = pwdEt.text.toString()

        if (viewModel.updateSignUpButtonState()) {
            Toast.makeText(this@SignUpActivity, "회원가입 성공\n이름: $name\n아이디: $id\n비밀번호: $pwd", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("pwd", pwd)
            // 이전 액티비티로 돌아가기 전에 현재 액티비티에서 처리한 결과를 담은 인텐트를 설정
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        } else {
            Toast.makeText(this@SignUpActivity, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}