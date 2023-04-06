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
    var imageArray = ArrayList<ImageView>()
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray = arrayListOf<ImageView>(
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

        val randomIndex = Random().nextInt(imageArray.size)

//        val imageView = findViewById<ImageView>(R.id.imageView)
//        imageView.setImageResource(imageIds[randomIndex])



        hideImages()


        // Countdown timer
        object : CountDownTimer(15000,500){

            override fun onTick(millisUntilFinished: Long) {
                binding.textTime.text = "Time : ${millisUntilFinished/1000}"
            }

            override fun onFinish() {

                binding.textTime.text = "Time : 0"
                handler.removeCallbacks(runnable)
                for (image in imageArray){
                   // image.visibility = View.INVISIBLE
                }

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Game over!")
                builder.setMessage("Your Score is : $score . " +
                        "\nDo you want to play again?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    // Restart
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                // show score buttons maybe
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(applicationContext, "Thanks for the game!", Toast.LENGTH_LONG).show()
                    // Close Game
                }
                builder.show()

            }

        }.start()
    }

    fun increaseScore(view: View){
        score++
        binding.textScore.text = "Score : $score"
    }

    fun decreaseScore(view: View){
        score--
        binding.textScore.text = "Score : $score"
    }

    private fun hideImages() {

        runnable = Runnable {
            for (image in imageArray) {
                image.visibility = View.INVISIBLE
            }

            val random = Random()
            val randomIndex = random.nextInt(9)
            imageArray[randomIndex].visibility = View.VISIBLE

            handler.postDelayed(runnable,500)
        }

        handler.post(runnable)

    }
}