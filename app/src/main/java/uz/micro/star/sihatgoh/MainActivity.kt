package uz.micro.star.sihatgoh

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import uz.micro.star.sihatgoh.fragments.MainFragment
import uz.xsoft.lesson4ict.viewmodel.FragmentViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: FragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[FragmentViewModel::class.java]
//        viewModel.apply {
//            layoutId = R.id.container
//            manager = supportFragmentManager
//            startMainFragment(MainFragment::class.java)
//        }
        viewModel.layoutId = R.id.container
        viewModel.manager = supportFragmentManager
        if (savedInstanceState==null){
            viewModel.startMainFragment(MainFragment::class.java)
        }
        viewModel.setFragmentChangeListener {
            Log.d("TTT", it)
        }
    }

    override fun onBackPressed() {
        if (viewModel.isOpenNav)
            viewModel.listenerMenu!!.invoke(true)
//            drawerLayout.closeDrawer(Gravity.LEFT)
        else
            super.onBackPressed()
    }
}