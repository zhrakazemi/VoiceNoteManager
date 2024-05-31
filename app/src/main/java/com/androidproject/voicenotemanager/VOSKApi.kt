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

/**
 * Singleton class responsible for voice recognition functionality using Vosk.
 *
 * This class leverages Hilt for dependency injection and implements the
 * `RecognitionListener` interface to handle recognition events. It manages loading
 * the Vosk model, starting/stopping recognition from the microphone, and provides
 * methods to control the recognition process.
 */
@Singleton
class VOSKApi @Inject constructor(
    /**
     * Application context obtained through dependency injection.
     */
    @ApplicationContext private val appContext: Context
) : RecognitionListener {

    /**
     * The loaded Vosk model for speech recognition.
     */
    var model: Model? = null

    /**
     * Flag indicating whether recognition from the microphone is paused.
     */
    var getPause: Boolean = true

    /**
     * Internal instance of the SpeechService for recognition (if active).
     */
    private var speechService: SpeechService? = null
    private val speechStreamService: SpeechStreamService? = null

    /**
     * The currently recognized text, accumulated from partial results.
     */
    var mainText: String = ""

    init {

        /**
         * Load the Vosk model asynchronously upon initialization. Consider using
         * coroutines for better asynchronous handling.
         */
        initModel()
        this.pause(true)
    }

    /**
     * Loads the Vosk model from the assets directory in a background thread.
     */
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

    /**
     * Loads the Vosk model from the assets directory in a background thread.
     */
    override fun onPartialResult(hypothesis: String?) {
        Log.d("TAG", "onPartialResult: $hypothesis")
    }

    /**
     * Invoked when a complete recognition result is available.
     *
     * @param hypothesis The complete hypothesis string.
     */
    override fun onResult(hypothesis: String?) {
        val split = hypothesis?.split("\"")
        if (split!![3] != "")
            mainText += split[3] + " "
    }

    /**
     * Invoked when the recognition process reaches its end.
     *
     * @param hypothesis The final hypothesis string (may be null).
     */
    override fun onFinalResult(hypothesis: String?) {
        Log.d("TAG", "onFinalResult: $hypothesis")
    }

    /**
     * Invoked when an error occurs during recognition.
     *
     * @param exception The exception object describing the error.
     */
    override fun onError(exception: Exception?) {
        Log.d("TAG", "onErrorResult: ${exception?.message}")
    }

    /**
     * Invoked when the recognition process times out.
     */
    override fun onTimeout() {
        Log.d("TAG", "onTimeResult")
    }

    /**
     * Invoked when the recognition process times out.
     */
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

    /**
     * Invoked when the recognition process times out.
     */
    fun pause(checked: Boolean) {
        if (speechService != null) {
            speechService!!.setPause(checked)
            getPause = checked
        }
    }
}