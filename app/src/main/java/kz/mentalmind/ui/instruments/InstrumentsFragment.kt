package kz.mentalmind.ui.instruments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.fragment_main.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.Page
import kz.mentalmind.ui.main.*

class InstrumentsFragment : Fragment(), ItemResultListener {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var potokAdapter: PotokAdapter
    private lateinit var lubimoeAdapter: LubimoeAdapter
    private var recommends: ArrayList<Page> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instruments, container, false)
        (activity as? MainActivity)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
    }

    private fun setList(){
        val ex1 = Page("Название инструмента", 1, "Описание инструмента", ResourcesCompat.getDrawable(resources, R.drawable.exampleimage1, null))
        val ex2 = Page("Название инструмента", 2, "Описание инструмента", ResourcesCompat.getDrawable(resources, R.drawable.exampleimage2, null))
        val ex3 = Page("Название инструмента", 3, "Описание инструмента", ResourcesCompat.getDrawable(resources, R.drawable.exampleimage3, null))
        recommends.addAll(
            listOf(
                ex1, ex2, ex3
            )
        )
    }

    private fun setAdapters(){
        setList()
        pageAdapter = PageAdapter(recommends)
        rvRecommend.adapter = pageAdapter
        potokAdapter = PotokAdapter(recommends)
        rvPotok.adapter = potokAdapter
        lubimoeAdapter = LubimoeAdapter(recommends)
        rvLovely.adapter = lubimoeAdapter
        pageAdapter.setItemResultListener(this)
        potokAdapter.setItemResultListener(this)
        lubimoeAdapter.setItemResultListener(this)
    }

    companion object {

    }

    override fun onItemClickedResult(page: Page) {
        (activity as? MainActivity)?.replaceFragment(MainItemFragment.newInstance(), MainItemFragment::class.simpleName)
    }
}