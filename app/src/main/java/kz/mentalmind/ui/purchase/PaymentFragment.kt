package kz.mentalmind.ui.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import kz.mentalmind.R


class PaymentFragment : Fragment() {

    companion object {
        const val LINK_TO_LOAD = "link_to_load"

        @JvmStatic
        fun newInstance(link: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(LINK_TO_LOAD, link)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linkToLoad = requireArguments().getString(LINK_TO_LOAD)
        val webView: WebView = view.findViewById(R.id.web_view)
        webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            domStorageEnabled = true
            databaseEnabled = true
        }
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(linkToLoad)
    }
}