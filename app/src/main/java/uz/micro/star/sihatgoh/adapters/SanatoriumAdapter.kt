package uz.micro.star.sihatgoh.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_sanatorium.view.*
import uz.micro.star.sihatgoh.R
import uz.micro.star.sihatgoh.models.SanatoriumData
import uz.xsoft.lesson2ict.MyDiffUtil
import java.io.ByteArrayInputStream

class SanatoriumAdapter(var data: ArrayList<SanatoriumData>) :
    RecyclerView.Adapter<SanatoriumAdapter.SanViewHolder>() {

    private var clickListener: ((Int, String) -> Unit)? = null

    fun setClickListener(f: (Int, String) -> Unit) {
        clickListener = f
    }

    fun setNewData(newData: List<SanatoriumData>) {
        val diff = DiffUtil.calculateDiff(MyDiffUtil(data, newData))
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
        fun bindData(data: SanatoriumData) {
            val inputStream = ByteArrayInputStream(data.src)//usi jeri
            val bitmap = BitmapFactory.decodeStream(inputStream)// jane
            itemView.image.setImageBitmap(bitmap)
            itemView.name.text = data.name
            itemView.description.text = data.description
            itemView.setOnClickListener {
                clickListener?.invoke(data.id, data.name)
            }
        }
    }
}