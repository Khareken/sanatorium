package uz.micro.star.appshipox.fragments

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.fragment_recommodation.*
import uz.micro.star.sihatgoh.R
import uz.micro.star.sihatgoh.adapters.SanatoriumAdapterNew
import uz.micro.star.sihatgoh.fragments.FullInfFragment
import uz.micro.star.sihatgoh.libs.Database
import uz.micro.star.sihatgoh.models.SanatoriumTinyData
import uz.xsoft.lesson4ict.fragments.BaseFragment
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class RecommodationFragment : BaseFragment(R.layout.fragment_recommodation) {
    private lateinit var data: ArrayList<SanatoriumTinyData>
    private lateinit var adapter: SanatoriumAdapterNew
    override fun onViewCreate() {

//        data=Database.getDatabase().sanatories
        data = ArrayList()
        adapter = SanatoriumAdapterNew(data,context!!)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        viewModel.repository.getSanatoriumTinyList().observe(this, Observer {
            adapter.setNewData(it)
        })


        //////////////////////////////////////////
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
        adapter.setClickListener { i, s ->
            viewModel.sanatoriumaId = i
            startMainFragmentX(FullInfFragment::class.java)
        }
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