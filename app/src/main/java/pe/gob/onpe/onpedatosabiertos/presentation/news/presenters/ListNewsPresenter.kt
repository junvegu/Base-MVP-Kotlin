package pe.gob.onpe.onpedatosabiertos.presentation.news.presenters

import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity
import pe.gob.onpe.onpedatosabiertos.data.source.datasources.NewsDataSource
import pe.gob.onpe.onpedatosabiertos.data.source.repositories.NewsRepository
import pe.gob.onpe.onpedatosabiertos.presentation.news.contracts.ListNewsContracts

/**
 * Created by junior on 19/03/18.
 */


class ListNewsPresenter(val newsRepository: NewsRepository, val newsView: ListNewsContracts.View) : ListNewsContracts.Presenter {


    private var firstLoad = true

    init {
        newsView.presenter = this
    }

    override fun start() {
        loadResult(false)
    }


    override fun loadResult(force: Boolean) {

        controllerLoader(force,true)


        newsRepository.getNewsList(object : NewsDataSource.LoadNewsCallback {
            override fun onDataNotAvailable() {

                controllerLoader(force,false)
                if (!newsView.isActive) {
                    return
                }
                newsView.showMessage("Nel pastel prro")

            }

            override fun onNewsLoaded(news: List<PublicationEntity>) {

                controllerLoader(force,false)
                if (!newsView.isActive) {
                    return
                }
                newsView.loadIndicator(false)
                newsView.showNews(news)
            }


        })

    }


    private fun controllerLoader(force :Boolean , status : Boolean){
        if (force)
            newsView.loadIndicator(status)
        else
            newsView.loaderInitialChargue(status)
    }

}