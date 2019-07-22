package ru.skillbranch.devintensive


import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var benderImage: ImageView//будет проинециализирована обязательно но в момент
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender
    //var benderImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //benderImage = findViewById(R.id.iv_bender) as ImageView
        //benderImage = findViewById(R.id.iv_bender)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send


        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name

        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        //textTxt.setText(benderObj.askQuestion())
        textTxt.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)

        messageEt.setOnEditorActionListener { _ , actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val checkAnswer = benderObj.checkCorrectAnswer(messageEt.text.toString())
                if ( checkAnswer == "-1") {
                    val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
                    messageEt.setText("")
                    val (r,g,b) = color
                    benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
                    textTxt.text = phrase
                } else {
                    textTxt.text = checkAnswer
                    messageEt.setText("")
                }
                if (isKeyboardOpen()) {

                    hideKeyboard()
                }
                true
            } else {
                false
            }
        }

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            val checkAnswer = benderObj.checkCorrectAnswer(messageEt.text.toString())
            if ( checkAnswer == "-1") {
                val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
                messageEt.setText("")
                val (r, g, b) = color
                benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
                textTxt.text = phrase
            } else {
                textTxt.text = checkAnswer
                messageEt.setText("")
            }
            if (isKeyboardOpen()) {
                Log.d("M_MainActivity", "keyboardOpen")
                hideKeyboard()
            }

        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)

    }

}
