package com.dbohdanov.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    val TAG = "taag"
    private lateinit var dataPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataPath = Utils.copyTessDataFiles(this)

        findViewById<TextView>(R.id.textview).text = getRecognizedText()
    }

//    private val DATA_PATH: String = "file:///android_assets/tessdata/"

    private fun getRecognizedText(): String {
        val tessBaseApi = TessBaseAPI()
        tessBaseApi.init(dataPath, "eng")
        tessBaseApi.setImage(getBitmap(this))
        val extractedText = tessBaseApi.utF8Text
        tessBaseApi.end()
        return extractedText
    }

    fun getBitmap(context: Context): Bitmap {
//        val str = "text.png"
//        val str = "dev1.jpg"
        val str = "dev2.png"
        return this.getBitmapFromAsset(context, str)!!
    }

    fun getBitmapFromAsset(context: Context, strName: String): Bitmap? {
        val assetManager = context.getAssets()
        val istr: InputStream
        var bitmap: Bitmap
        try {
            istr = assetManager.open(strName)
            bitmap = BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            return null
        }

        return bitmap
    }
}
