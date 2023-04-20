package com.example.groupproject.data.leaderboard

class ScoresData(private val newScore: Int) {
    private val scoresArr = IntArray(5)

    init {
        if (newScore > scoresArr[0]) {
            scoresArr[0] = newScore
        }
        else if (newScore > scoresArr[1]) {
            scoresArr[1] = newScore
        }
        else if (newScore > scoresArr[2]) {
            scoresArr[2] = newScore
        }
        else if (newScore > scoresArr[3]) {
            scoresArr[3] = newScore
        }
        else if (newScore > scoresArr[4]) {
            scoresArr[4] = newScore
        }
    }

    // Getter method for scoresArr
    fun getScoresArr(): IntArray {
        return scoresArr
    }
}
