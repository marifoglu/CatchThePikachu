package com.darth.application

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.darth.application.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    var score = 0
    var pikachuArray = ArrayList<ImageView>()
    var psyduckArray = ArrayList<ImageView>()
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        pikachuArray = arrayListOf<ImageView>(
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5,
            binding.imageView6,
            binding.imageView7,
            binding.imageView8,
            binding.imageView9
        )

        psyduckArray = arrayListOf<ImageView>(
            binding.psyduckImageView1,
            binding.psyduckImageView2,
            binding.psyduckImageView3,
            binding.psyduckImageView4,
            binding.psyduckImageView5,
            binding.psyduckImageView6,
            binding.psyduckImageView7,
            binding.psyduckImageView8,
            binding.psyduckImageView9
        )



        hideImages()

        // Countdown timer
        object : CountDownTimer(15000, 500) {

            override fun onTick(millisUntilFinished: Long) {
                binding.textTime.text = "Time : ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {

                binding.textTime.text = "Time : 0"
                handler.removeCallbacks(runnable)
                for (image in pikachuArray) {
                    image.visibility = View.INVISIBLE
                }

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Game over!")
                builder.setMessage(
                    "Your Score is : $score . " +
                            "\nDo you want to play again?"
                )
                builder.setPositiveButton("Yes") { dialog, which ->
                    // Restart
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                // show score buttons maybe
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        "Thanks for the game!",
                        Toast.LENGTH_LONG
                    ).show()
                    // Close Game
                }
                builder.show()

            }

        }.start()
    }

    fun increaseScore(view: View) {
        score++
        binding.textScore.text = "Score : $score"
    }

    fun decreaseScore(view: View) {
        score--
        binding.textScore.text = "Score : $score"
    }

    private fun hideImages() {
        var lastPikachuIndex = -1 // lets find! the last shown Pikachu image
        val random = Random()

        runnable = Runnable {
            for (pikachu in pikachuArray) {
                pikachu.visibility = View.INVISIBLE
            }

            for (psyduck in psyduckArray){
                psyduck.visibility = View.INVISIBLE
            }

            if (random.nextInt(4) != 0) {
                // Generate a random index
                var randomIndex = random.nextInt(pikachuArray.size - 1)
                if (randomIndex >= lastPikachuIndex) randomIndex++ // avoiding showing the same Pikachu
                lastPikachuIndex = randomIndex
                pikachuArray[randomIndex].visibility = View.VISIBLE
            } else {
                // Psyduck array ...
                val randomIndex = random.nextInt(psyduckArray.size)
                psyduckArray[randomIndex].visibility = View.VISIBLE
            }

            handler.postDelayed(runnable, 500)
        }

        handler.post(runnable)
    }
}