package com.example.hangman

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.LevelListDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var passwords: Array<String>
    lateinit var currentPassword: String
    var currentLevel = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        passwords = resources.getStringArray(R.array.passwords)
        currentPassword = passwords.random()
        setPassword()
        submitButton.setOnClickListener {
            val letter = letterInput.text.toString()
            letterInput.setText("")
            if (currentPassword.contains(letter)){
                showLetters(letter)
            }
            else{
                drawHangman()
            }
            addUsedLetter(letter)
            if (checkWin()){
                val toast = Toast.makeText(applicationContext, "You won", Toast.LENGTH_SHORT)
                toast.show()
            }
            else{
                if (checkLose()){
                    val toast = Toast.makeText(applicationContext, "You lost", Toast.LENGTH_SHORT)
                    toast.show()
                    reset()
                }
            }

        }
    }

    private fun reset(){
        drawHangman()
        currentPassword = passwords.random()
        setPassword()
        usedLetters.text = ""
    }

    @SuppressLint("SetTextI18n")
    private fun setPassword(){
        password.text = "_ ".repeat(currentPassword.length)
    }

    @SuppressLint("SetTextI18n")
    private fun addUsedLetter(letter: String){
        if (!usedLetters.text.contains(letter)){
            usedLetters.text = "${usedLetters.text} $letter "
        }

    }

    private fun showLetters(letter: String){
        var result = ""
        currentPassword.forEach{sign ->
            result += when {
                letter == sign.toString() -> "$letter "
                usedLetters.text.contains(sign) -> "$sign "
                else -> "_ "
            }
        }
        password.text = result
    }

    private fun drawHangman(){
        currentLevel = (currentLevel + 1) % 11
        imageView.setImageLevel(currentLevel)
    }

    private fun checkWin(): Boolean{
        if (!password.text.contains('_')){
            return true
        }
        return false
    }

    private fun checkLose(): Boolean{
        if (currentLevel == 10){
            return true
        }
        return false
    }


}
