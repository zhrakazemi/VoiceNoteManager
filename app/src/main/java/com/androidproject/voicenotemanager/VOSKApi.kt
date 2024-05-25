package com.androidproject.voicenotemanager

import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import java.io.IOException


class VOSKApi : RecognitionListener {


    private var model: Model? = null
    var getPause : Boolean = true
    private var speechService: SpeechService? = null
    private val speechStreamService: SpeechStreamService? = null
    var mainText : String = ""

    init {
        this.recognizeMicrophone()
        this.pause(true)
    }

    private fun initModel() {
     /*   StorageService.unpack(
            , "model-en-us", "model",
            StorageService.Callback<Model> { model: Model? ->
                this.model =
                    model
            },
            StorageService.Callback<IOException> { exception: IOException ->
            })*/
    }
    override fun onPartialResult(hypothesis: String?) {
        TODO("Not yet implemented")
    }

    override fun onResult(hypothesis: String?) {
      mainText = mainText + hypothesis
    }

    override fun onFinalResult(hypothesis: String?) {
        TODO("Not yet implemented")
    }

    override fun onError(exception: Exception?) {
        TODO("Not yet implemented")
    }

    override fun onTimeout() {
        TODO("Not yet implemented")
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
            } catch (e: IOException) {

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