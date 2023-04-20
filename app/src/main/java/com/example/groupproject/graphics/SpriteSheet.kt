package com.example.groupproject.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.groupproject.R

class SpriteSheet(context: Context) {
    val bitmap: Bitmap

    init {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        val bitmap1 =
            BitmapFactory.decodeResource(context.resources, R.drawable.sprite_sheet_transparent, bitmapOptions)
        bitmap = Bitmap.createScaledBitmap(bitmap1, 2404*3, 130*3, false);
        //bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_sheet, bitmapOptions)
    }
//1426,100,1335,1
    val dinoSpriteArray: Array<Sprite>
        get() {
            return arrayOf(
                Sprite(this, Rect(1510*3, 0,  1600*3, 90*3)),
                Sprite(this, Rect(1510*3 +90*3, 0,  1600*3 +90*3,90*3)),
                Sprite(this, Rect(1510*3, 0,  1600*3, 90*3)),
                Sprite(this, Rect(1510*3 +90*5, 0,  1600*3+90*5, 90*3))
//                Sprite(this, Rect(1950, 0,  2100, 90*3)),
//                Sprite(this, Rect(1950, 0,  2100, 90*3)),
//                Sprite(this, Rect(1950, 0,  2100, 90*3))


            )
        }

    val groundSprite : Sprite
        get(){
            return Sprite(this, Rect(1000, 300,  2404*3, 130*3))
        }


    val treeSpriteArray: Array<Sprite>
        get() {
            return arrayOf(
                Sprite(this, Rect(1950, 0,  2100, 90*3)),
                Sprite(this, Rect(1950 +150, 0,  2100+150, 90*3)),
                Sprite(this, Rect(1950+150*2, 0,  2100+150*2, 90*3))
            )
        }



    private fun getSpriteByIndex(idxRow: Int, idxCol: Int): Sprite {
        return Sprite(
            this, Rect(
                idxCol * SPRITE_WIDTH_PIXELS,
                idxRow * SPRITE_HEIGHT_PIXELS,
                (idxCol + 1) * SPRITE_WIDTH_PIXELS,
                (idxRow + 1) * SPRITE_HEIGHT_PIXELS
            )
        )
    }

    companion object {
        private const val SPRITE_WIDTH_PIXELS = 64
        private const val SPRITE_HEIGHT_PIXELS = 64
    }
}