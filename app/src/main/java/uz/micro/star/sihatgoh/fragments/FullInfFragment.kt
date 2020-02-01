package uz.micro.star.sihatgoh.fragments

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.fragment_full_inf.*
import uz.micro.star.sihatgoh.R
import uz.micro.star.sihatgoh.libs.Database
import uz.xsoft.lesson4ict.fragments.BaseFragment
import java.io.*
import java.util.*

class FullInfFragment : BaseFragment(R.layout.fragment_full_inf) {
    override fun onViewCreate() {
        val url_maps = Database.getDatabase().sanatoriesImages
        url_maps.forEach { image ->
            var textSliderView = TextSliderView(context)
            val inputStream = ByteArrayInputStream(url_maps.get(image.key))
            val bitmap = BitmapFactory.decodeStream(inputStream)
            textSliderView
                .description(image.key)
                .image(bitmapToFile(bitmap))
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener {
                    Toast.makeText(context, image.key, Toast.LENGTH_SHORT).show()
                }
            textSliderView.bundle(Bundle())
            textSliderView.bundle
                .putString("extra", image.key)
            imageSlider.addSlider(textSliderView)
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut)
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        imageSlider.setCustomAnimation(DescriptionAnimation())
        imageSlider.setDuration(5000)
        ///////////////////////
        mainMenu.setOnClickListener {
            //            viewModel.listenerMenu!!.invoke(true)
            closeActiveFragment()
        }
        goToApplication.setOnClickListener {
            closeActiveFragment()
            viewModel.listenerLastFragment!!.invoke(2)
            startMainFragment(MainFragment::class.java)
        }
        locationSanatorium.setOnClickListener {
            closeActiveFragment()
            viewModel.listenerLastFragment!!.invoke(3)
            startMainFragment(MainFragment::class.java)
        }
        getFullData(viewModel.sanatoriumaId)
    }

    fun getFullData(id: Int) {
//        val fullDataById = Database.getDatabase().getFullDataById(id)
        val fullDataById = viewModel.repository.getSanatoryOffline(id)
        sanatoriumName.text = fullDataById.name
        sanLocation.text = fullDataById.location
        sanNumber.text = fullDataById.tel_number
        sanFax.text = fullDataById.fax
        sanMail.text = fullDataById.email_number
        sanCost.text = fullDataById.cost.toString()
        content.text = fullDataById.content
        emptyPlace.text = fullDataById.place_count.toString()
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        // Get the context wrapper
        val wrapper = ContextWrapper(context)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // Return the saved bitmap uri
        return file
    }

    override fun onStop() {
        imageSlider.stopAutoCycle()
        super.onStop()
    }
}