package com.example.chesstimer.timer


import android.os.CountDownTimer
import android.widget.TextView

import com.example.chesstimer.common.TimerUtils
import com.example.chesstimer.common.timer.GameState

class Timers(var gameTime: Long,
             private val bottomPrimaryTimer : TextView,
             private val topPrimaryTimer : TextView,
             private val bottomSecondaryTimer : TextView,
             private val topSecondaryTimer : TextView){

    private var bottomPlayerTimeLeft = gameTime
    private var topPlayerTimeLeft = gameTime

    private var timerTop : CountDownTimer? = null
    private var timerBottom : CountDownTimer? = null

    init {
        prepareTimers()
    }

    fun resetTimers(){
        bottomPlayerTimeLeft = gameTime
        topPlayerTimeLeft = gameTime
        prepareTimers()
        updateTimers()
    }

    fun pausedTimers(){
        timerBottom?.cancel()
        timerTop?.cancel()
    }

    fun startTimer(gameState: GameState) {
        when(gameState){
            GameState.PLAYER_TOP -> {
                prepareTimerTopTimer()
                timerTop?.start()
                timerBottom?.cancel()
            } GameState.PLAYER_BOTTOM -> {
                prepareTimerBottomTimer()
                timerTop?.cancel()
                timerBottom?.start()
            }
        }
    }

    private fun updateTopTimer(timeLeft: Long) {
        val timeLeftFormatted = TimerUtils.getTimeLeftFormatted(timeLeft)
        topPrimaryTimer.text = timeLeftFormatted
        bottomSecondaryTimer.text = timeLeftFormatted
    }

    private fun updateBottomTimer(timeLeft: Long) {
        val timeLeftFormatted = TimerUtils.getTimeLeftFormatted(timeLeft)
        bottomPrimaryTimer.text = timeLeftFormatted
        topSecondaryTimer.text = timeLeftFormatted
    }

    private fun prepareTimers(){
        prepareTimerTopTimer()
        prepareTimerBottomTimer()
    }

    private fun updateTimers(){
        updateTopTimer(gameTime)
        updateBottomTimer(gameTime)
    }

    private fun prepareTimerTopTimer(){
        timerTop = object : CountDownTimer(topPlayerTimeLeft , TimerUtils.secondsToMillis(1)){
            override fun onFinish() {
                timerTop?.cancel()
            }

            override fun onTick(millisUntilFinished: Long) {
                topPlayerTimeLeft = millisUntilFinished
                updateTopTimer(millisUntilFinished)
            }
        }
    }

    private fun prepareTimerBottomTimer() {
        timerBottom = object : CountDownTimer(bottomPlayerTimeLeft, 1000) {
            override fun onFinish() {
                timerBottom?.cancel()
            }

            override fun onTick(millisUntilFinished: Long) {
                bottomPlayerTimeLeft = millisUntilFinished
                updateBottomTimer(millisUntilFinished)
            }
        }
    }
}