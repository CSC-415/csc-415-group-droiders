package com.example.groupproject

import android.content.Context
import android.content.Intent
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.groupproject.gameobject.Circle
import com.example.groupproject.gameobject.Dino
import com.example.groupproject.gameobject.Ground
import com.example.groupproject.gameobject.Obstacle
import com.example.groupproject.gamepanel.GameOver
import com.example.groupproject.gamepanel.Performance
import com.example.groupproject.graphics.Animator
import com.example.groupproject.graphics.SpriteSheet

class Game(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback {


    private val dino: Dino
    private var gameLoop: GameLoop
    private val performance: Performance
    private var gameDisplay: GameDisplay
    private var paint2= Paint()
    private var gameOver = false
    private val ground : Ground
    private var obstacleList: MutableList<Obstacle>
    private var animator: Animator
    private var score:Int
    private var hiScore:Int



    init{
        score = 0
        hiScore=0
        // Get surface holder and add callback
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(context,this, surfaceHolder)
        // Initialize game panels
        performance = Performance(context, gameLoop)
        // Initialize game objects
        val spriteSheet = SpriteSheet(context)





        // Initialize display and center it around the player
        val displayMetrics = DisplayMetrics()
        animator = Animator(spriteSheet.dinoSpriteArray, spriteSheet.treeSpriteArray,spriteSheet.groundSprite)
        dino = Dino(context, 170.0, 660.0, 45.0, animator)
        ground = Ground(context, 2404.0*3,0.0, 900.0,  32.0, animator)
        gameDisplay = GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, dino)
        paint2.color = Color.WHITE
        paint2.style = Paint.Style.FILL
        obstacleList = ArrayList<Obstacle>()

    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        // Draw game panels
        canvas.drawPaint(paint2)


        performance.draw(canvas)
        ground.draw(canvas,gameDisplay)
        dino.draw(canvas,gameDisplay)
        for(obstacle in obstacleList){
            obstacle.draw(canvas, gameDisplay)
        }
        dino.draw(canvas,gameDisplay)

        val paint = Paint()
        val color: Int = ContextCompat.getColor(context, R.color.teal_200)
        paint.color = color
        paint.textSize = 50f


        val scoreText = "Score: $score"
        canvas.drawText(scoreText, width - paint.measureText(scoreText) - 50f, 200f, paint)
        val hiScoreText = "High Score: $hiScore"
        canvas.drawText(hiScoreText, width - paint.measureText(hiScoreText) - 50f, 150f, paint)


        if(dino.getIsDead()){

        }

    }

    fun update() {

            score += 1
            dino.update()
            ground.update()
            gameDisplay.update()

            // Spawn enemy
            if (Obstacle.readyToSpawn()) {

                obstacleList += Obstacle(
                    context,
                    (2000..4000).random().toDouble(),
                    660.0,
                    75.0,
                    animator,
                    dino
                );

            }

            for (obstacle in obstacleList) {

                if(score < 1000){
                    obstacle.velocityX = 20 + 0.02 * score
                }
                else {
                    obstacle.velocityX = 20 + 0.03 * score
                }
//
                obstacle.update()


            }


            // Iterate through obstacleList and Check for collision between each obstacle and the dino
        // Check for collision between each obstacle and the dino
        for (obstacle in obstacleList) {
            if (Circle.isColliding(obstacle, dino)) {
                if (!gameOver) {
                    gameOver = true
                    gameOver()
                }
                return
            }
        }
        }




    override fun onTouchEvent(event: MotionEvent): Boolean {

        // Handle user input touch event actions
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                dino.jump()
            }
            MotionEvent.ACTION_MOVE -> {
                dino.positionY = event.x.toDouble();
                dino.positionY = event.y.toDouble();
                //Log.d("touch", "ACTION_Move");

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
            }
        }
        return super.onTouchEvent(event)
    }

    fun gameOver() {
        pause()
        // Show a message to the user that the game is over
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Game Over")
                .setMessage("Your score is $score, the current high-score is: $hiScore")
                .setPositiveButton("Restart") { _, _ ->
                    // Restart the game
                    if(score>hiScore) {
                        hiScore=score
                    }

                        score = 0

                    dino.positionY = 660.0
                    obstacleList.clear()
                    gameOver = false // reset game over flag
                    gameLoop.startLoop()
                }
                .setNegativeButton("Menu") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)

                }
                .setCancelable(false)
                .create()

            alertDialog.show()
        }
    }


    private fun sleep() {
        Thread.sleep(200)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("Game.java", "surfaceCreated()")
        if (gameLoop.equals(Thread.State.TERMINATED)) {
            val surfaceHolder = getHolder()
            surfaceHolder.addCallback(this)
            gameLoop = GameLoop(context, this, surfaceHolder)
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Game.java", "surfaceChanged()")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("Game.java", "surfaceDestroyed()")
        gameLoop.stopLoop()


    }


    fun pause() {
        gameLoop.stopLoop()
    }



}
