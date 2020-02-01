package uz.micro.star.appshipox.fragments

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_sanatorium.*
import uz.micro.star.sihatgoh.R
import uz.micro.star.sihatgoh.adapters.SanatoriumAdapter
import uz.micro.star.sihatgoh.adapters.SanatoriumAdapterNew
import uz.micro.star.sihatgoh.dialogs.SearchSanDialog
import uz.micro.star.sihatgoh.fragments.FullInfFragment
import uz.micro.star.sihatgoh.libs.Database
import uz.micro.star.sihatgoh.models.SanatoriumData
import uz.micro.star.sihatgoh.models.SanatoriumTinyData
import uz.xsoft.lesson4ict.fragments.BaseFragment

class SanatoriumFragment : BaseFragment(R.layout.fragment_sanatorium) {
    private lateinit var data: ArrayList<SanatoriumTinyData>
    private lateinit var adapter: SanatoriumAdapterNew

    override fun onViewCreate() {
//        data= Database.getDatabase().sanatories
        data = ArrayList()
        adapter = SanatoriumAdapterNew(data,context!!)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        viewModel.repository.getSanatoriumTinyList().observe(this, Observer {
            adapter.setNewData(it)
        })
        adapter.setClickListener { i, s ->
            viewModel.sanatoriumaId=i
            startMainFragmentX(FullInfFragment::class.java)
        }
        ////////////////////////////
    }
}
