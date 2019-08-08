package io.github.takusan23.niconicocommentcanvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        post_button.setOnClickListener {
            comment_canvas.postComment(comment_edittext.text.toString())
        }
        kusa.setOnClickListener {
            comment_canvas.postComment(kusa.text.toString())
        }
        pati.setOnClickListener {
            comment_canvas.postComment(pati.text.toString())
        }
        yonne.setOnClickListener {
            comment_canvas.postComment(yonne.text.toString())
        }
        kita.setOnClickListener {
            comment_canvas.postComment(kita.text.toString())
        }

    }
}
