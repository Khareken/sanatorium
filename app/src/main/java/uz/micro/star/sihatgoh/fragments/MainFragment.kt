package uz.micro.star.sihatgoh.fragments

import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.fragment_main.*
import uz.micro.star.appshipox.fragments.AppilationFragment
import uz.micro.star.appshipox.fragments.MapFragment
import uz.micro.star.appshipox.fragments.RecommodationFragment
import uz.micro.star.appshipox.fragments.SanatoriumFragment
import uz.micro.star.sihatgoh.R
import uz.micro.star.sihatgoh.dialogs.SearchSanDialog
import uz.micro.star.sihatgoh.adapters.PagerAdapter
import uz.xsoft.lesson4ict.fragments.BaseFragment

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private lateinit var fragments: ArrayList<BaseFragment>
    lateinit var adapter: PagerAdapter
    override fun onViewCreate() {

        viewModel.repository.getSanatoriumList().observe(this, Observer {
//            Toast.makeText(context,it.size.toString(),Toast.LENGTH_SHORT).show()
        })
        viewModel.repository.getSanatoriumTinyList().observe(this, Observer {
//            Toast.makeText(context,it.size.toString(),Toast.LENGTH_SHORT).show()
        })

        addFrament()
        adapter = PagerAdapter(activity!!.supportFragmentManager, fragments)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottomNav.show(position)
            }
        })

        bottomNav.setOnClickMenuListener {
            viewPager.currentItem = it.id
        }
        viewModel.setLastFragment {
            bottomNav.show(it)
            viewPager.currentItem = it
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
                viewModel.isOpenNav = false
            }

            override fun onDrawerOpened(drawerView: View) {
                viewModel.isOpenNav = true
            }
        })
        ///////////////////////////
        viewModel.setManuListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                drawerLayout.closeDrawer(Gravity.LEFT)
            else
                drawerLayout.openDrawer(Gravity.LEFT)

        }
        navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_recommodation -> {
                    bottomNav.show(0)
                    viewPager.currentItem = 0
                }
                R.id.navigation_sanatorium -> {
                    bottomNav.show(1)
                    viewPager.currentItem = 1
                }
                R.id.navigation_application -> {
                    bottomNav.show(2)
                    viewPager.currentItem = 2
                }
                R.id.navigation_map -> {
                    bottomNav.show(3)
                    viewPager.currentItem = 3
                }
            }
            drawerLayout.closeDrawer(Gravity.LEFT)
            true
        }
        searchSan.setOnClickListener {
            var search = SearchSanDialog(
                context!!,
                viewModel.repository.getTinySanatoryOffline()
            )
            search.setOnDialogClickListener { name, id ->
                viewModel.sanatoriumaId = id
                startMainFragmentX(FullInfFragment::class.java)
            }
            search.show()
        }
        mainMenu.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
    }

    private fun addFrament() {
        fragments = ArrayList()
        fragments.add(RecommodationFragment())
        fragments.add(SanatoriumFragment())
        fragments.add(AppilationFragment())
        fragments.add(MapFragment())
        //////////////////////
        bottomNav.add(MeowBottomNavigation.Model(0, R.drawable.home))
        bottomNav.add(MeowBottomNavigation.Model(1, R.drawable.sunset))
        bottomNav.add(MeowBottomNavigation.Model(2, R.drawable.writing))
        bottomNav.add(MeowBottomNavigation.Model(3, R.drawable.place))
        bottomNav.show(0)
    }
}