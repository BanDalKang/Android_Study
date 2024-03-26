package com.example.introduction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val idEt: EditText = findViewById(R.id.id_ET)
        val pwdEt: EditText = findViewById(R.id.pwd_ET)
        val signinBtn: Button = findViewById(R.id.signin_btn)
        val signupBtn: Button = findViewById(R.id.signup_btn)

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                // ?. 연산자를 사용해 data가 null이면 getStringExtra()를 호출하지 않고 null을 반환
                // ?: 연산자를 사용해 피연산자가 null을 반환하면 ""을 대체값으로 사용
                val id = result.data?.getStringExtra("id") ?: ""
                val pwd = result.data?.getStringExtra("pwd") ?: ""
                idEt.setText(id)
                pwdEt.setText(pwd)
            }
        }

        // 로그인 버튼
        signinBtn.setOnClickListener {
            val id = idEt.text.toString()
            val pwd = pwdEt.text.toString()

            if (id.isNotEmpty() && pwd.isNotEmpty()) {
                Toast.makeText(this@SignInActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            } else {
                Toast.makeText(this@SignInActivity, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 회원가입 버튼
        signupBtn.setOnClickListener {
            startForResult.launch(Intent(this, SignUpActivity::class.java))
        }
    }
}
