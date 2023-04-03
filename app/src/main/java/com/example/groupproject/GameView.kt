package com.example.groupproject

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.SurfaceView

class GameView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), Runnable {
    private var onScoreUpdateListener: ((score: Int) -> Unit)? = null
    private var thread: Thread? = null
    private var isPlaying = false
    private var dinoX = 0
    private var score=0
    private var isGameOver=false
    private var obstacleX=0
    private var obstacleY=100
    private val dinosaurBitmap = BitmapFactory.decodeResource(resources, R.drawable.trex)
    private val obstacleBitmap = BitmapFactory.decodeResource(resources, R.drawable.cact)

    companion object {
        private const val DINO_SPEED = 10
        private const val DINO_Y = 100
        private const val OBSTACLE_SPEED=5
        private const val OBSTACLE_INITIAL_X = 1000
        private const val OBSTACLE_GAP = 500
        private const val OBSTACLE_MIN_Y = 50
        private const val OBSTACLE_MAX_Y = 350
    }

    // Method to check for collisions with obstacles
    private fun isCollisionDetected(): Boolean {
        val dinoRect = Rect(dinoX, DINO_Y, dinoX + dinosaurBitmap.width, DINO_Y + dinosaurBitmap.height)
        val obstacleRect = Rect(obstacleX, obstacleY, obstacleX + obstacleBitmap.width, obstacleY + obstacleBitmap.height)
        return dinoRect.intersect(obstacleRect)
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            // Draw the dinosaur
            canvas.drawBitmap(dinosaurBitmap, dinoX.toFloat(), DINO_Y.toFloat(), null)
            // Draw the obstacle
            canvas.drawBitmap(obstacleBitmap, obstacleX.toFloat(), obstacleY.toFloat(), null)
            // Draw the player's score on the canvas
            val paint = Paint()
            paint.color = Color.BLACK
            paint.textSize = 40f
            canvas.drawText("Score: $score", 10f, 50f, paint)
            holder.unlockCanvasAndPost(canvas)
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

    private fun update() {
        dinoX += DINO_SPEED
        obstacleX -= OBSTACLE_SPEED

        // Check if the obstacle has reached the end of the screen
        if (obstacleX < -obstacleBitmap.width) {
            // Reset the obstacle to the right side of the screen with a random Y position
            obstacleX = OBSTACLE_INITIAL_X
            obstacleY = (OBSTACLE_MIN_Y..OBSTACLE_MAX_Y).random()
            score++
        }

        if (isCollisionDetected()) {
            gameOver()
        }
        onScoreUpdateListener?.invoke(score)

    }
    fun setOnScoreUpdateListener(listener: (score: Int) -> Unit) {
        onScoreUpdateListener = listener
    }

    private fun sleep() {
        Thread.sleep(16)//60fps
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread!!.start()
    }

    fun pause() {
        try {
            isPlaying = false
            thread!!.join()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}