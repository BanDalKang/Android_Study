package com.example.introduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val nameText: TextView = findViewById(R.id.name_TV)
        val idText: TextView = findViewById(R.id.id_TV)
        val ageText: TextView = findViewById(R.id.age_TV)
        val mbtiText: TextView = findViewById(R.id.mbti_TV)
        val finishButton: Button = findViewById(R.id.finish_btn)

        val name = "강현정"
        val age = "26"
        val mbti = "ISTP"

        nameText.text = "이름: $name"
        if(intent.hasExtra("id")) {
            idText.text = "아이디 : "+ intent.getStringExtra("id")
        }
        ageText.text = "나이: $age"
        mbtiText.text = "MBTI: $mbti"

        // 이미지뷰
        val imageView: ImageView = findViewById(R.id.logo_IV)
        val images = arrayOf(
            R.drawable.ic_home1,
            R.drawable.ic_home2,
            R.drawable.ic_home3,
            R.drawable.ic_home4,
            R.drawable.ic_home5
        )
        val randomImags = images.random()
        imageView.setImageResource(randomImags)

        // 해설 예제 코드
        /*val id = when ((1..6).random()) {
            1 -> R.drawable.ca1
            2 -> R.drawable.ca2
            3 -> R.drawable.ca3
            4 -> R.drawable.ca4
            5 -> R.drawable.ca5
            else -> R.drawable.ca1
        }
        iv_logo.setImageDrawable(ResourcesCompat.getDrawable(resources, id, null))*/

        // 종료 버튼
        finishButton.setOnClickListener {
            finish()
        }
    }
}