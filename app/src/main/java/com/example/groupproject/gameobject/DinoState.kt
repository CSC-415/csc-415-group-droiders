package com.example.groupproject.gameobject

class DinoState(private val dino: Dino) {
    enum class State {
        NOT_MOVING, STARED_MOVING, IS_MOVING, IS_DEAD
    }

    var state: State
        private set

    init {
        state = State.NOT_MOVING
    }

    fun update() {
        when (state) {
            State.NOT_MOVING -> if (dino.velocityY === 0.0) state =
                State.STARED_MOVING
            State.STARED_MOVING -> if (dino.velocityY === 0.0) state =
                State.IS_MOVING
            State.IS_MOVING -> if (dino.velocityY !== 0.0) state =
                State.NOT_MOVING
            State.IS_DEAD -> if (dino.getIsDead()) state =
                State.IS_DEAD
            else -> {}
        }
    }
}