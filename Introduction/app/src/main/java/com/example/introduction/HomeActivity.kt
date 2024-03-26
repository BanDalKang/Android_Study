package com.example.introduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imageView: ImageView = findViewById(R.id.logo_IV)
        val nameText: TextView = findViewById(R.id.name_TV)
        val idText: TextView = findViewById(R.id.id_TV)
        val ageText: TextView = findViewById(R.id.age_TV)
        val mbtiText: TextView = findViewById(R.id.mbti_TV)
        val finishButton: Button = findViewById(R.id.finish_btn)

        val name = "강현정"
        val id = intent.getStringExtra("id")
        val age = "26"
        val mbti = "ISTP"
        val images = arrayOf(
            R.drawable.ic_home1,
            R.drawable.ic_home2,
            R.drawable.ic_home3,
            R.drawable.ic_home4,
            R.drawable.ic_home5
        )
        val randomImags = images.random()

        nameText.text = "이름: $name"
        idText.text = "아이디: $id"
        ageText.text = "나이: $age"
        mbtiText.text = "MBTI: $mbti"
        imageView.setImageResource(randomImags)

        // 종료 버튼
        finishButton.setOnClickListener {
            finish()
        }
    }
}