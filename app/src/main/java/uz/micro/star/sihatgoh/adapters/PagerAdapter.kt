package uz.micro.star.sihatgoh.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.xsoft.lesson4ict.fragments.BaseFragment

class PagerAdapter(fm: FragmentManager, private var behavior: List<BaseFragment>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = behavior[position]

    override fun getCount() = behavior.size


}