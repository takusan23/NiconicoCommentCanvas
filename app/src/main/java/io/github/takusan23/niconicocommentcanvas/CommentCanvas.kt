package io.github.takusan23.niconicocommentcanvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.concurrent.schedule
import android.view.Display
import androidx.appcompat.app.AppCompatActivity


class CommentCanvas(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paint: Paint
    var x = 0
    var y = 0

    val textList = arrayListOf<String>()
    val xList = arrayListOf<Int>()
    val yList = arrayListOf<Int>()

    //いまコメントが流れてる座標を保存する
    val commentFlowingXList = arrayListOf<Int>()
    val commentFlowingYList = arrayListOf<Int>()

    init {
        paint = Paint()
        paint.textSize = 50F


        Timer().schedule(10, 10) {
            for (i in 0..(xList.size - 1)) {
                val x = xList.get(i) - 3
                if (x > -1000) {
                    xList.set(i, x)
                } else {
                    //見えなくなったら配列から消す
                    //textList.removeAt(i)
                    //xList.removeAt(i)
                    //yList.removeAt(i)
                }
            }
            invalidate()
        }

/*
        Timer().schedule(100, 100) {
            x -= 10
            invalidate()
        }
*/

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        commentFlowingXList.clear()
        commentFlowingYList.clear()
        for (i in 0..(xList.size - 1)) {
            val text = textList.get(i)
            val x = xList.get(i)
            val y = yList.get(i)
            if (x > -1000) {
                commentFlowingXList.add(x)
                commentFlowingYList.add(y)
                canvas?.drawText(text, x.toFloat(), y.toFloat(), paint)
            }
        }
    }

    /*
    * コメント投稿
    * */
    fun postComment(comment: String) {
        textList.add(comment)
        val display = (context as AppCompatActivity).getWindowManager().getDefaultDisplay()
        val point = Point()
        display.getSize(point)

        val display_width = point.x

        val x = point.x
        var commentY = 100

        val paint = Paint()

        var text = ""
        textList.forEach {
            text = it
        }

        //コメントの段一個ずつみる
        for (i in 0..(commentFlowingXList.size - 1)) {
            val y = commentFlowingYList.get(i)
            val x = commentFlowingXList.get(i)
            val text_size = paint.measureText(text).toInt()

            commentY = checkCommentArea(x, text_size, display_width, comment, y + 50, y)

        }
        System.out.println(paint.measureText(comment).toInt())

        xList.add(x)
        yList.add(commentY)


    }

    //高さを返す
    //after = 表示できないので一個下の高さ
    //before = 表示できるのでそのまま
    fun checkCommentArea(x: Int, text_size: Int, display_width: Int, comment: String, after: Int, before: Int): Int {
        val paint = Paint()
        //コメントが流れてる先を計算する
        // |------□□□    | コメントを□として
        // |<---------->    | この範囲
        val comment_go_width = x + text_size

        //コメントが流れた後の距離を計算する
        // |------□□□    | コメントを□として
        // |            <-->| この範囲
        val comment_air_width = display_width - comment_go_width

        //これから流すコメントの長さを計算する
        // |------□□□    | コメントを□として
        // |      <---->    | この範囲
        val comment_text_width = paint.measureText(comment)

        //コメントが流れた後の距離とコメントの長さを比較して
        //通過後よりコメントが大きいときは２段めに表示
        if (comment_air_width < comment_text_width) {
            return after
        } else {
            //そのまま表示
            return before
        }
    }


}