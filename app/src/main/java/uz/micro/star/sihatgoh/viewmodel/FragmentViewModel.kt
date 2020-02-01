package uz.xsoft.lesson4ict.viewmodel

import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.micro.star.sihatgoh.R
import uz.xsoft.lesson4ict.fragments.BaseFragment
import uz.xsoft.lesson4ict.repository.AppRepository

class FragmentViewModel : ViewModel() {
    private val fragments = HashMap<String, BaseFragment>()
    lateinit var manager: FragmentManager
    val inputTextLiveData = MutableLiveData<String>()
    val activeFragment = MutableLiveData<String>()
    var menuListener = MutableLiveData<Int>()
    var isOpenNav = false
    var sanatoriumaId=0
    val repository = AppRepository()
    private var listener: ((String) -> Unit)? = null
    var listenerLastFragment: ((Int) -> Unit)? = null
    var listenerMenu: ((Boolean) -> Unit)? = null

    @LayoutRes
    var layoutId: Int = 0

    fun <T : BaseFragment> startFragment(fragment: Class<T>) {
        manager.beginTransaction().addToBackStack(fragment.toString())
            .replace(layoutId, getFragment(fragment)).commit()
    }

    fun <T : BaseFragment> startFragmentX(fragment: Class<T>) {
        if (fragments.contains(fragment.name)) {
            manager.beginTransaction().replace(layoutId, getFragment(fragment)).commit()
        } else {
            manager.beginTransaction().addToBackStack(fragment.toString())
                .replace(layoutId, getFragment(fragment)).commit()
        }
    }

    fun <T : BaseFragment> startMainFragment(fragment: Class<T>) {
        manager.beginTransaction().replace(layoutId, getFragment(fragment)).commit()
    }

    fun <T : BaseFragment> startMainFragmentX(fragment: Class<T>) {
        manager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_top,
            R.anim.slide_out_bottom,
            R.anim.slide_in_top,
            R.anim.slide_out_bottom
        ).addToBackStack(getFragment(fragment).toString())
            .add(layoutId, getFragment(fragment)).commit()
    }

    private fun <T : BaseFragment> getFragment(fragment: Class<T>): BaseFragment {
        listener?.invoke(fragment.name)
        if (!fragments.contains(fragment.name)) {
            fragments[fragment.name] = fragment.newInstance()
        }
        return fragments[fragment.name]!!
    }

    fun setFragmentChangeListener(f: (String) -> Unit) {
        listener = f
    }

    fun setManuListener(f: (Boolean) -> Unit) {
        listenerMenu = f
    }

    fun setLastFragment(f: (Int) -> Unit) {
        listenerLastFragment = f
    }

    fun closeActiveFragment() {
        if (fragments.size > 0) manager.popBackStack()
    }
}