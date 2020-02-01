package uz.xsoft.lesson2ict

import androidx.recyclerview.widget.DiffUtil
import uz.micro.star.sihatgoh.models.SanatoriumData


/**
 * Created by Sherzodbek on 22.09.2018 in Lesson2ICT.
 */
class MyDiffUtil(var oldList: List<SanatoriumData>, var newList: List<SanatoriumData>) : DiffUtil.Callback() {
    override fun areItemsTheSame(p0: Int, p1: Int) = oldList[p0].name == newList[p1].name
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areContentsTheSame(p0: Int, p1: Int) =
        oldList[p0].name == newList[p1].name &&
                oldList[p0].description == newList[p1].description
//                && oldList[p0].trainerCode == newList[p1].trainerCode
}