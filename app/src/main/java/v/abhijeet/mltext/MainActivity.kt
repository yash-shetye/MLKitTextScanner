package v.abhijeet.mltext

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore

import android.widget.Toast
import com.google.mlkit.vision.common.InputImage

import com.google.mlkit.vision.text.TextRecognition.getClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1


    private lateinit var imageBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        captureimage.setOnClickListener {
            dispatchTakePictureIntent()
            textdisplay.setText("")
        }

        detecttext_btn.setOnClickListener {

            detecttextFromImage()
        }

    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
             imageBitmap = data?.extras?.get("data") as Bitmap
            imageview.setImageBitmap(imageBitmap)


        }
    }

    private fun detecttextFromImage() {


        val image = InputImage.fromBitmap(imageBitmap, 0)
        val recognizer = getClient()

            val task = recognizer.process(image)
            task.let {
                recognizer.process(image)
                        .addOnSuccessListener { visionText ->

                            Toast.makeText(this, visionText.text, Toast.LENGTH_SHORT).show()
                            textdisplay.setText(visionText.text)
                        }
                        .addOnFailureListener { e ->

                            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                        }

            }
        }
    }

