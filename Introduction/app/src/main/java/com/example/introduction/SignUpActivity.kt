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
        viewModel.password.observe(this, Observer { inputName->
            nameEt.setText(inputName)
        })

        pwdEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!viewModel.isStrongPassword(pwdEt.text.toString())){
                    pwd_warningTV.visibility = View.VISIBLE
                    signupBtn.isEnabled = false
                } else {
                    pwd_warningTV.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        pwdcheckEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!checkPasswordMatch()){
                    pwd_check_warningTV.visibility = View.VISIBLE

                } else {
                    pwd_check_warningTV.visibility = View.GONE
                    signupBtn.isEnabled = true
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        signupBtn.isEnabled = false
        signupBtn.setOnClickListener {
            signUpButtonClick()
        }
    }

    private fun signUpButtonClick() {
        val name = nameEt.text.toString()
        val id = idEt.text.toString()
        val pwd = pwdEt.text.toString()

        if (name.isNotEmpty() && id.isNotEmpty() && pwd.isNotEmpty()) {
            viewModel.setUserValue(name, id, pwd)
            Toast.makeText(this@SignUpActivity, "회원가입 성공\n이름: $name\n아이디: $id\n비밀번호: $pwd", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("pwd", pwd)
            // 이전 액티비티로 돌아가기 전에 현재 액티비티에서 처리한 결과를 담은 인텐트를 설정
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this@SignUpActivity, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPasswordMatch(): Boolean {
        val password = pwdEt.text.toString()
        val passwordCheck = pwdcheckEt.text.toString()

        return password == passwordCheck
    }
}