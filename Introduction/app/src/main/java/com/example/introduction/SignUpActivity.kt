package com.example.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        val nameEt: EditText = findViewById(R.id.name_ET)
        val idEt: EditText = findViewById(R.id.id_ET)
        val pwdEt: EditText = findViewById(R.id.pwd_ET)
        val signupBtn: Button = findViewById(R.id.signup_btn)

        // LiveData를 관찰하여 변경 사항을 처리
        viewModel.name.observe(this, Observer { name ->
            nameEt.setText(name)
        })

        viewModel.id.observe(this, Observer { id ->
            idEt.setText(id)
        })

        viewModel.password.observe(this, Observer { password ->
            pwdEt.setText(password)
        })

        signupBtn.setOnClickListener {
            val name = nameEt.text.toString()
            val id = idEt.text.toString()
            val pwd = pwdEt.text.toString()

            viewModel.setName(name)
            viewModel.setId(id)
            viewModel.setPassword(pwd)

            if (viewModel.isValidInput()) {
                Toast.makeText(this@SignUpActivity, "회원가입 성공\n이름: $name\n아이디: $id\n비밀번호: $pwd", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("pwd", pwd)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@SignUpActivity, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}