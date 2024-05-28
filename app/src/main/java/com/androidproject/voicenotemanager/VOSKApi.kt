package com.androidproject.voicenotemanager

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VOSKApi @Inject constructor(
    @ApplicationContext private val appContext: Context
) : RecognitionListener {

    var model: Model? = null
    var getPause: Boolean = true
    private var speechService: SpeechService? = null
    private val speechStreamService: SpeechStreamService? = null
    var mainText: String = ""

    init {
        initModel()
        this.pause(true)
    }

    private fun initModel() {
        StorageService.unpack(
            appContext, "model-en-us", "model",
            { model: Model? ->
                this.model = model
            },
            { exception: IOException ->
                Log.d("file error", exception.toString())
            })
    }

    override fun onPartialResult(hypothesis: String?) {
        Log.d("TAG", "onPartialResult: $hypothesis")
    }

    override fun onResult(hypothesis: String?) {
        val split = hypothesis?.split("\"")
        if (split!![3] != "")
            mainText += split[3] + " "
    }

    override fun onFinalResult(hypothesis: String?) {
        Log.d("TAG", "onFinalResult: $hypothesis")
    }

    override fun onError(exception: Exception?) {
        Log.d("TAG", "onErrorResult: ${exception?.message}")
    }

    override fun onTimeout() {
        Log.d("TAG", "onTimeResult")
    }

    fun recognizeMicrophone() {
        if (speechService != null) {
            speechService!!.stop()
            speechService = null
        } else {
            try {
                val rec = Recognizer(model, 16000.0f)
                speechService = SpeechService(rec, 16000.0f)
                speechService!!.startListening(this)
            } catch (_: IOException) {

            }
        }
    }


    fun pause(checked: Boolean) {
        if (speechService != null) {
            speechService!!.setPause(checked)
            getPause = checked
        }
    }
}