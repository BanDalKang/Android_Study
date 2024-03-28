package com.example.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
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

    private val pwd_warningTV: TextView by lazy {
        findViewById(R.id.pwd_warning_TV)
    }

    private val pwd_check_warningTV: TextView by lazy {
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
            pwd_warningTV.visibility = if (isPasswordValid) {
                View.GONE // 비밀번호가 강력한 경우 텍스트뷰 숨기기
            } else {
                View.VISIBLE // 비밀번호가 강력하지 않은 경우 텍스트뷰 표시
            }
        }

        viewModel.isPasswordMatch.observe(this) { isPasswordMatch ->
            pwd_check_warningTV.visibility = if (isPasswordMatch) {
                View.GONE // 비밀번호가 강력한 경우 텍스트뷰 숨기기
            } else {
                View.VISIBLE // 비밀번호가 강력하지 않은 경우 텍스트뷰 표시
            }
        }

        // EditText들의 텍스트가 변경될 때마다 ViewModel에 값을 업데이트
        nameEt.addTextChangedListener {
            viewModel.setName(it.toString())
        }

        idEt.addTextChangedListener {
            viewModel.setId(it.toString())
        }

        pwdEt.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        pwdcheckEt.addTextChangedListener {
            viewModel.setPasswordCheck(it.toString())
        }

        signupBtn.setOnClickListener {
            signUpButtonClick()
        }
    }

    private fun signUpButtonClick() {
        val name = viewModel.name.value
        val id = viewModel.id.value
        val pwd = viewModel.password.value

        viewModel.updateSignUpButtonState()

        viewModel.isSignUpEnabled.observe(this) { isSignUpEnabled ->
            if (isSignUpEnabled) {
                Toast.makeText(this@SignUpActivity, "회원가입 성공\n이름: $name\n아이디: $id\n비밀번호: $pwd", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("pwd", pwd)
                // 이전 액티비티로 돌아가기 전에 현재 액티비티에서 처리한 결과를 담은 인텐트를 설정
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@SignUpActivity, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}