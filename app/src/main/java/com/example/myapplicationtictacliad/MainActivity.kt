package com.example.myapplicationtictacliad

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var activePlayer = 0 // 0 for X, 1 for O
    private var gameState = IntArray(9) { 2 }
    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableEdgeToEdge()

        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val playAgainButton: Button = findViewById(R.id.PlayAgain)
        val playerTextView: TextView = findViewById(R.id.Player)

        val buttons = arrayOf(
            button1, button2, button3,
            button4, button5, button6,
            button7, button8, button9
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleButtonClick(button, index, playerTextView)
            }
        }

        playAgainButton.setOnClickListener {
            playAgain(playerTextView, buttons, playAgainButton)
        }
    }

    private fun handleButtonClick(button: Button, index: Int, playerTextView: TextView) {
        if (gameState[index] == 2 && gameActive) {
            gameState[index] = activePlayer

            if (activePlayer == 0) {
                button.text = getString(R.string.x)
                activePlayer = 1
            } else {
                button.text = getString(R.string.o)
                activePlayer = 0
            }

            val playerTurn = if (activePlayer == 0) getString(R.string.x) else getString(R.string.o)
            playerTextView.text = getString(R.string.turn_message, playerTurn)

            checkWinner(playerTextView)
        }
    }

    private fun checkWinner(playerTextView: TextView) {
        val playAgainButton: Button = findViewById(R.id.PlayAgain)
        val buttons = arrayOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )

        for (winningPosition in winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                gameState[winningPosition[0]] != 2) {

                gameActive = false
                val winner = if (gameState[winningPosition[0]] == 0) getString(R.string.x) else getString(R.string.o)
                playerTextView.text = getString(R.string.winner_message, winner)
                playAgainButton.visibility = View.VISIBLE
                buttons.forEach { it.isEnabled = false }
                return
            }
        }

        if (gameState.all { it != 2 }) {
            playerTextView.text = getString(R.string.tie_message)
            playAgainButton.visibility = View.VISIBLE
        }
    }

    private fun playAgain(playerTextView: TextView, buttons: Array<Button>, playAgainButton: Button) {
        gameActive = true
        activePlayer = 0
        gameState.fill(2)

        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }

        playAgainButton.visibility = View.GONE
        playerTextView.text = getString(R.string.turn_message, getString(R.string.x))
    }
}