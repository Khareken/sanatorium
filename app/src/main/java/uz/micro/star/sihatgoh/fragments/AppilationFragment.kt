package uz.micro.star.appshipox.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_application.*
import uz.micro.star.sihatgoh.R
import uz.xsoft.lesson4ict.fragments.BaseFragment
import uz.xsoft.lesson4ict.room.AppDatabase
import java.util.*

class AppilationFragment : BaseFragment(R.layout.fragment_application) {
    var country = arrayOf("Qon aylanish tizimi kasalliklari",
        "Nerv sistemasi kasalliklari", "Genekologik kasalliklar", "Androligik kasalliklar")
    val GALLERY_REQUEST_CODE = 111
    override fun onViewCreate() {

        pickImage.setOnClickListener {
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_REQUEST_CODE
            )
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(context, "Iltimos ruhsat bering !", Toast.LENGTH_SHORT).show()
            } else {
                pickFromGallery()
            }
        }
        sendApplication.setOnClickListener {
            var ok = true
            var group: ViewGroup = liner
            for (i in 0..group.childCount - 3) {
                if (TextUtils.isEmpty((group.getChildAt(i) as EditText).text)) {
                    (group.getChildAt(i) as EditText).error = "Maydon bo`sh"
                    return@setOnClickListener
                }
                if (passpordImage.drawable == null) {
                    Toast.makeText(context, "Passport rasmini kiriting", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            var dialog = AlertDialog.Builder(context)
            dialog.setTitle("Xabar")
            dialog.setMessage(
                "Siz ro`yhatdan o`tdingiz ! \nQuyidagi kod orqali to`lovni amalga oshirishingiz mumkin:\n" + Random().nextInt(
                    9999999 - 1256398
                ) + 1256398
            )
            dialog.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                dialog.dismiss()
                var group: ViewGroup = liner
                for (i in 0..group.childCount - 3) {
                    (group.getChildAt(i) as EditText).setText("")
                    passpordImage.setImageBitmap(null)
                }
            }
            var d = dialog.create()
            d.show()
        }
        //////////////
        val tinySanatoryOffline = AppDatabase.database!!.getInputData().getTinySanatoryOffline()
        val s = arrayOfNulls<String>(tinySanatoryOffline.size)
        var k = 0
        tinySanatoryOffline.forEach {
            s[k++] = it.name
        }
        val aa = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, s)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = aa

        val bb = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, country)
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = bb
    }

    private fun pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        val intent = Intent(Intent.ACTION_PICK)
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.type = "image/*"
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var selectedImage = data!!.data
            passpordImage.setImageURI(selectedImage)
//            pickImage.isVisible = false
        }
    }
}
