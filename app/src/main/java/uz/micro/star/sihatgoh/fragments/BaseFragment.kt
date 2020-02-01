package uz.xsoft.lesson4ict.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import uz.xsoft.lesson4ict.viewmodel.FragmentViewModel

abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment() {
    protected lateinit var viewModel: FragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!)[FragmentViewModel::class.java]
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        animate(view)
        onViewCreate()
    }

    override fun onResume() {
        super.onResume()

    }
    fun <T : BaseFragment> startFragment(fragment: Class<T>) {
        viewModel.startFragment(fragment)
    }

    fun <T : BaseFragment> startMainFragment(fragment: Class<T>) {
        viewModel.startMainFragment(fragment)
    }
    fun <T : BaseFragment> startMainFragmentX(fragment: Class<T>) {
        viewModel.startMainFragmentX(fragment)
    }
    fun closeActiveFragment(){
        viewModel.closeActiveFragment()
    }
    abstract fun onViewCreate()

//    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
//        return CubeAnimation.create(CubeAnimation.UP, enter, 200)
//    }

//    fun animate(view: View) {
//        var a = 1200f
//        view.translationY = a
//        view.animate().translationYBy(a * -1).setDuration(500).start()
//    }
fun animate(view: View) {
    var a = 750f
    view.translationX = a
    view.animate().translationXBy(a * -1).setDuration(500).start()
}

}