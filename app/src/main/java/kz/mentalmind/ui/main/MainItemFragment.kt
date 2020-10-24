package kz.mentalmind.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.fragment_main.*
import kz.mentalmind.R
import kz.mentalmind.data.Page

class MainItemFragment : Fragment() {

    private lateinit var mainAdapter: MainAdapter
    private var recommends: ArrayList<Page> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
    }

    private fun setList() {
        val ex1 = Page(
            "Название инструмента",
            1,
            "Описание инструмента",
            ResourcesCompat.getDrawable(resources, R.drawable.exampleimage1, null)
        )
        val ex2 = Page(
            "Название инструмента",
            2,
            "Описание инструмента",
            ResourcesCompat.getDrawable(resources, R.drawable.exampleimage2, null)
        )
        val ex3 = Page(
            "Название инструмента",
            3,
            "Описание инструмента",
            ResourcesCompat.getDrawable(resources, R.drawable.exampleimage3, null)
        )
        recommends.addAll(
            listOf(
                ex1, ex2, ex3
            )
        )
    }

    private fun setAdapters() {
        setList()
        rvRecommend.adapter = mainAdapter
    }

    companion object {
        fun newInstance() = MainItemFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}