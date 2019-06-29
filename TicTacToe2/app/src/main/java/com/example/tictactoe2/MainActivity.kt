package com.example.tictactoe2

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import android.util.DisplayMetrics
import android.util.Log
import java.lang.Integer.min


class MainActivity : AppCompatActivity() {

    private var currentPlayer: Int
    private var players: List<Player> = listOf(Player("x", 0xff00ff00.toInt()),
        Player("o", 0xffff0000.toInt()))
    private var won = false

    init {
        currentPlayer = Random.nextInt(0, players.size - 1)
    }

    @SuppressLint("SetTextI18n", "PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playAgain.setOnClickListener(({resetBoard(board)}))
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val orientation = resources.configuration.orientation


        var size = min(height, width) / 5
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            size = min(height, width) / 8

        }


        for (i in 0 until board.childCount) {
            Log.println(Log.INFO, "Jamnik", board.getChildAt(i).toString())
            val row = board.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val button = row.getChildAt(j) as Button

                button.layoutParams = TableRow.LayoutParams(
                    size,
                    size,
                    1.0f
                )
                button.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
                button.setOnClickListener(({
                    if (button.text == "") {
                        button.text = players[currentPlayer].sign
                        button.setBackgroundColor(players[currentPlayer].color)
                        if(this.checkWin(board) and !won){
                            won = true
                            val toast = Toast.makeText(
                                this,
                                "Player ${currentPlayer + 1} has won",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                        this.nextTurn()
                    }
                }))
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun resetBoard(board: TableLayout){
        for (i in 0 until board.childCount){
            val row = board.getChildAt(i) as LinearLayout
            for (j in 0 until row.childCount){
                val cell = row.getChildAt(j) as Button
                cell.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
                cell.text = ""
            }
        }
        won = false
    }

    private fun nextTurn(){
        currentPlayer = (currentPlayer + 1) % players.size
    }

    private fun checkWin(board: LinearLayout): Boolean{
        val noRows = board.childCount

        if (noRows > 1) {
            val row = board.getChildAt(0) as LinearLayout
            val noColumns = row.childCount
            if (checkColumns(board, noRows, noColumns) ||
                checkRows(board, noRows, noColumns) ||
                checkDiagonals(board, noRows - 1, noColumns - 1)){
                return true
            }
        }
        return false
    }

    private fun checkRows(board: LinearLayout, height: Int, width: Int): Boolean {
        for (rowIndex in 0 until height) {
            val row = board.getChildAt(rowIndex) as LinearLayout
            val firstCell = row.getChildAt(0) as Button
            val firstSign = firstCell.text
            if (firstSign != ""){
                var win = true
                for (colIndex in 1 until width){
                    val cell = row.getChildAt(colIndex) as Button
                    if (cell.text != firstSign){
                        win = false
                    }
                }
                if(win){
                    return true
                }
            }

        }
        return false
    }

    private fun checkDiagonals(board: LinearLayout, height: Int, width: Int): Boolean{
        val length = (width + height) / 2
        return checkDiagonal(board, 0, 0, 1, length) || checkDiagonal(board, 0, width, -1,length)
    }

    private fun checkDiagonal(board:LinearLayout, startingRow: Int, startingCol: Int, step: Int, length: Int): Boolean{
        val rowTmp = board.getChildAt(startingRow ) as LinearLayout
        val buttonTmp = rowTmp.getChildAt(startingCol) as Button
        val firstSign = buttonTmp.text
        if (firstSign != ""){
            for (i in 1..length){
                val row = board.getChildAt(startingRow + i) as LinearLayout
                val button = row.getChildAt(startingCol + step * i ) as Button
                if (firstSign != button.text){
                    return false
                }
            }
            return true
        }
        return false
    }

    private fun checkColumns(board: LinearLayout, height: Int, width: Int): Boolean{
        for (colIndex in 0 until width) {
            val row = board.getChildAt(0) as LinearLayout
            val firstCell = row.getChildAt(colIndex) as Button
            val firstSign = firstCell.text
            if (firstSign != ""){
                var win = true
                for (rowIndex in 1 until height){
                    var cell = (board.getChildAt(rowIndex) as LinearLayout).getChildAt(colIndex) as Button
                    if (cell.text != firstSign){
                        win = false
                    }
                }
                if(win){
                    return true
                }
            }

        }
        return false
    }
}

open class Player(var sign: String, var color: Int)


