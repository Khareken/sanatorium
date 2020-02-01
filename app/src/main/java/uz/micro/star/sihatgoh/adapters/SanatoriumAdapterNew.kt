package uz.micro.star.sihatgoh.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_sanatorium.view.*
import uz.micro.star.sihatgoh.R
import uz.micro.star.sihatgoh.models.SanatoriumTinyData
import uz.xsoft.lesson2ict.MyDiffUtilNew

class SanatoriumAdapterNew(var data: ArrayList<SanatoriumTinyData>, var ctx: Context) :
    RecyclerView.Adapter<SanatoriumAdapterNew.SanViewHolder>() {

    private var clickListener: ((Int, String) -> Unit)? = null

    fun setClickListener(f: (Int, String) -> Unit) {
        clickListener = f
    }

    fun setNewData(newData: List<SanatoriumTinyData>) {
        val diff = DiffUtil.calculateDiff(MyDiffUtilNew(data, newData))
        data.clear()
        data.addAll(newData)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SanViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sanatorium,
                parent,
                false
            )
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SanViewHolder, position: Int) =
        holder.bindData(data.get(position))


    inner class SanViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bindData(data: SanatoriumTinyData) {
//            val inputStream = ByteArrayInputStream(data.src)
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            itemView.image.setImageBitmap(bitmap)
            Glide.with(ctx)  //2
//                .load("https://www.harley-davidson.com/content/dam/h-d/images/motorcycles/my19/touring/road-glide-special/details/hdi/19-touring-road-glide-special-hdi-hero.jpg") //3
                .load(data.src) //3
//                .centerCrop() //4
                .placeholder(R.drawable.background_1) //5
                .error(R.drawable.background_2) //6

//                .fallback(R.drawable.ic_no_image) //7
                .into(itemView.image)
            Log.d("YYY", data.src)
            itemView.name.text = data.name
            itemView.description.text = data.description
            itemView.setOnClickListener {
                clickListener?.invoke(data.id, data.name)
            }
        }
    }
}