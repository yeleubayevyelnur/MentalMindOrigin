package kz.mentalmind.ui.purchase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_tariffs.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Tariff
import org.koin.androidx.viewmodel.ext.android.viewModel

class TariffsFragment : Fragment() {

    private val tariffsViewModel: TariffsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private val tariffList = mutableListOf<Tariff>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariffs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tariffsRv = view.findViewById<RecyclerView>(R.id.tariffs)
        val tariffsAdapter = TariffsAdapter(tariffList)
        tariffsRv.adapter = tariffsAdapter

        tariffsViewModel.getToken()?.let {
            tariffsViewModel.getTariffs(it)
        }
        close.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }


//        val textShader1 = LinearGradient(
//            0F,
//            0F,
//            0F,
//            20F,
//            intArrayOf(
//                ContextCompat.getColor(requireContext(), R.color.gr1),
//                ContextCompat.getColor(requireContext(), R.color.gr2)
//            ),
//            floatArrayOf(0F, 1F),
//            Shader.TileMode.CLAMP
//        )
//        val textShader2 = LinearGradient(
//            0F,
//            0F,
//            0F,
//            20F,
//            intArrayOf(
//                ContextCompat.getColor(requireContext(), R.color.gr3),
//                ContextCompat.getColor(requireContext(), R.color.gr4)
//            ),
//            floatArrayOf(0F, 1F),
//            Shader.TileMode.CLAMP
//        )
//        btnSub1.paint.shader = textShader1
//        btnSub2.paint.shader = textShader2
        compositeDisposable.add(
            tariffsViewModel.observeTariffsSubject().subscribe({
                tariffList.clear()
                tariffList.addAll(it.results)
                tariffsAdapter.notifyDataSetChanged()
            }, {

            })
        )
        compositeDisposable.add(tariffsViewModel.observeErrorSubject().subscribe { error ->
            (activity as? MainActivity)?.alertDialog(requireContext(), error)
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onDetach() {
        compositeDisposable.clear()
        super.onDetach()
        (activity as? MainActivity)?.showBottomNavigation()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TariffsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}