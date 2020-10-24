package kz.mentalmind.ui.create

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

class CreateFragment : Fragment() {

    private lateinit var mainAdapter: MainAdapter
    private lateinit var potokAdapter: PotokAdapter
    private lateinit var lubimoeAdapter: LubimoeAdapter
    private lateinit var affirmationAdapter: AffirmationAdapter
    private var recommends: ArrayList<Page> = arrayListOf()
    private var affirmations: ArrayList<Page> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
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
        potokAdapter = PotokAdapter(recommends)
        lubimoeAdapter = LubimoeAdapter(recommends)
        affirmationAdapter = AffirmationAdapter(affirmations)
    }

    companion object {

    }
}