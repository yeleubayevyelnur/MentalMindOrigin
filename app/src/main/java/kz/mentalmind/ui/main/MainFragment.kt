package kz.mentalmind.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.fragment_main.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.Category
import kz.mentalmind.data.Page
import kz.mentalmind.ui.main.mood.MoodFragment

class MainFragment : Fragment(), ItemResultListener {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var potokAdapter: PotokAdapter
    private lateinit var lubimoeAdapter: LubimoeAdapter
    private lateinit var affirmationAdapter: AffirmationAdapter
    private var recommends: ArrayList<Page> = arrayListOf()
    private var affirmations: ArrayList<Page> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? MainActivity)?.hideActionBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       btnMood.setOnClickListener {
           (activity as? MainActivity)?.replaceFragment(MoodFragment(), MoodFragment::class.simpleName)
       }
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
        val aff1 = Page("", 1, "", ResourcesCompat.getDrawable(resources, R.drawable.affir1, null))
        val aff2 = Page("", 2, "", ResourcesCompat.getDrawable(resources, R.drawable.affir2, null))
        val aff3 = Page("", 3, "", ResourcesCompat.getDrawable(resources, R.drawable.affir1, null))
        affirmations.addAll(
            listOf(
                aff1, aff2, aff3
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
        affirmationAdapter = AffirmationAdapter(affirmations)
        rvAffirmations.adapter = affirmationAdapter
        pageAdapter.setItemResultListener(this)
        potokAdapter.setItemResultListener(this)
        lubimoeAdapter.setItemResultListener(this)
        affirmationAdapter.setItemResultListener(this)
    }

    companion object {

    }

    override fun onItemClickedResult(page: Page) {
        (activity as? MainActivity)?.replaceFragment(MainItemFragment.newInstance(), MainItemFragment::class.simpleName)
    }
}