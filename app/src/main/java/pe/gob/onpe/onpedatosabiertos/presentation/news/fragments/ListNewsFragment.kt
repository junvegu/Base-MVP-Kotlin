package pe.gob.onpe.onpedatosabiertos.presentation.news.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_back.*
import kotlinx.android.synthetic.main.fragment_list_news.*
import pe.gob.onpe.onpedatosabiertos.Injection
import pe.gob.onpe.onpedatosabiertos.R
import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity
import pe.gob.onpe.onpedatosabiertos.presentation.news.adapters.NewsAdapter
import pe.gob.onpe.onpedatosabiertos.presentation.news.contracts.ListNewsContracts
import pe.gob.onpe.onpedatosabiertos.presentation.news.presenters.ListNewsPresenter
import pe.gob.onpe.onpedatosabiertos.utils.showSnackBar

/**
 * Created by junior on 19/03/18.
 */

class ListNewsFragment : Fragment(), ListNewsContracts.View {


    private val listNewsAdapter = NewsAdapter(ArrayList()) {
        showMessage("Click on ${it.title}")
    }

    override lateinit var presenter: ListNewsContracts.Presenter


    override fun showMessage(msg: String) {
        view?.showSnackBar(msg, Snackbar.LENGTH_LONG)
    }

    override fun loadIndicator(status: Boolean) {
        val root = view ?: return
        with(refresh_layout) {
            post { isRefreshing = status }
        }

    }

    override fun showNews(items: List<PublicationEntity>) {

        listNewsAdapter.newsItems = items
    }

    override var isActive: Boolean = false
        get() = isAdded


    override fun loaderInitialChargue(active: Boolean) {
        if (active) {
            layout_loader.visibility = View.VISIBLE
            refresh_layout.isEnabled = false
        } else {
            layout_loader.visibility = View.GONE
            refresh_layout.isEnabled = true
        }
    }

    //New Instance
    companion object {

        fun newInstance(): ListNewsFragment {
            return ListNewsFragment()
        }
    }


    //Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ListNewsPresenter(Injection.provideNewsRepository(context), this)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news_list.layoutManager = LinearLayoutManager(context)
        news_list.adapter = listNewsAdapter

        refresh_layout.apply {
            scrollUpChild = news_list
            setOnRefreshListener {
                presenter.loadResult(true)
            }
        }
    }


}