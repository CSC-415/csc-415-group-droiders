package com.example.groupproject

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt
import kotlin.random.Random

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), Runnable {

    private var isPlaying = false
    private var isGameOver = false
    private var thread: Thread? = null
    private val paint = Paint()

    private var score = 0
    private var distanceTraveled = 0
    private var dinoX = 0
    private var dinoY = 0
    private var obstacleX = 500
    private var obstacleY = 100

    private var onScoreUpdateListener: ((score: Int) -> Unit)? = null

    private val dinoBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.trex)
    private val obstacleBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bird)
   // private val birdBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bird)
    private val scaledDinoBitmap: Bitmap = Bitmap.createScaledBitmap(dinoBitmap, 200,200, true)
    private val scaledObstacleBitmap: Bitmap = Bitmap.createScaledBitmap(obstacleBitmap, 100,100, true)
    //private val scaledBirdBitmap: Bitmap = Bitmap.createScaledBitmap(birdBitmap, 100,100, true)


    private var isJumping = false
    private var jumpVelocity = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // handle touch down event
                performClick() // call performClick when a click is detected
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // handle touch move event
                return true
            }
            MotionEvent.ACTION_UP -> {
                // handle touch up event
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        // handle click event
        return true
    }
    init {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // Set up initial position of dinosaur and obstacle
                dinoX = 0
                dinoY = height - scaledDinoBitmap.height
                obstacleX = width
                obstacleY = height - scaledObstacleBitmap.height

                // Start the game loop
                resume()
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                // Pause the game loop
                pause()
            }
        })

    }

    private fun draw() {
        if (holder.surface.isValid) {
            // Lock the canvas before drawing
            val canvas = holder.lockCanvas()

            // Create a new canvas for double buffering
            val buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val bufferCanvas = Canvas(buffer)

            // Draw the game objects onto the buffer canvas
            bufferCanvas.drawColor(Color.WHITE)

            // Draw the path on a separate canvas layer
            val pathBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val pathCanvas = Canvas(pathBitmap)
            paint.color = Color.BLACK
            paint.strokeWidth = 5f
            pathCanvas.drawLine(0f,970f, width.toFloat(), 970f, paint)

            // Draw the dinosaur on the buffer canvas
            bufferCanvas.drawBitmap(scaledDinoBitmap, dinoX.toFloat(), dinoY.toFloat(), null)

            // Draw the obstacle on the buffer canvas
            bufferCanvas.drawBitmap(scaledObstacleBitmap, obstacleX.toFloat(), obstacleY.toFloat(), null)

            // Draw the score on the buffer canvas
            paint.color = Color.BLACK
            paint.textSize = 50f
            val scoreText = "Score: $score"
            bufferCanvas.drawText(scoreText, width - paint.measureText(scoreText) - 50f, 100f, paint)

            // Draw the path bitmap onto the buffer canvas
            bufferCanvas.drawBitmap(pathBitmap, 0f, 0f, null)

            // Draw the buffer canvas onto the main canvas
            canvas.drawBitmap(buffer, 0f, 0f, null)

            // Unlock the canvas after drawing
            holder.unlockCanvasAndPost(canvas)
        }
    }


    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }

        if (isGameOver) {
            gameOver()
        }
    }

    private fun update() {
        // Update the positions of the dino and obstacle
        //dinoX += 10
        obstacleX -= 10
        distanceTraveled+=10

        // Update the position of the dino if it's jumping
        if (isJumping) {
            dinoY -= jumpVelocity.roundToInt()
            jumpVelocity -= 1f
            // If the dino has landed, stop jumping
            if (dinoY >= height - scaledDinoBitmap.height) {
                dinoY = height - scaledDinoBitmap.height
                isJumping = false
            }
        } else {
            // Check for touch events to make the dino jump
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isJumping = true
                        jumpVelocity = 20f
                        performClick()
                        true
                    }
                    else -> false
                }
            }
        }
        // Check for collision
        if (dinoX + scaledDinoBitmap.width > obstacleX && dinoX < obstacleX + scaledObstacleBitmap.width &&
            dinoY + scaledDinoBitmap.height > obstacleY && dinoY < obstacleY + scaledObstacleBitmap.height
        ) {
            gameOver()
        }

        // Increase the score based on the distance moved by the dino
        if (distanceTraveled >= 50) {
            score++
            distanceTraveled -= 50
            onScoreUpdateListener?.invoke(score)
        }
        // Check if the obstacle has passed the screen
        if (obstacleX < 0) {
            obstacleX = width + Random.nextInt(500)
            obstacleY = Random.nextInt(height - scaledObstacleBitmap.height)
        }
    }



        private fun gameOver() {
            isGameOver = true
            isPlaying = false

            // Show a message to the user that the game is over
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Game Over")
                    .setMessage("Your score is $score")
                    .setPositiveButton("Restart") { _, _ ->
                        // Restart the game
                        isGameOver = false
                        isPlaying = true
                        score = 0
                        dinoX = 0
                        obstacleX = 500
                        obstacleY = 100
                        resume()
                    }
                    .setCancelable(false)
                    .create()

                alertDialog.show()
            }
        }

        private fun sleep() {
            Thread.sleep(16)//60fps
        }

        fun resume() {
            isPlaying = true
            thread = Thread(this)
            thread?.start()
        }

        fun pause() {
            try {
                isPlaying = false
                thread?.join()
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }

    fun setOnScoreUpdateListener(listener: (score: Int) -> Unit) {
        onScoreUpdateListener = listener
    }
}
