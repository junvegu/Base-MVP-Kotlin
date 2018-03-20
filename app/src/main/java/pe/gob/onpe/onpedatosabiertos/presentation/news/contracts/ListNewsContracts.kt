package pe.gob.onpe.onpedatosabiertos.presentation.news.contracts

import pe.gob.onpe.onpedatosabiertos.base.BasePresenter
import pe.gob.onpe.onpedatosabiertos.base.BaseView
import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity

/**
 * Created by junior on 19/03/18.
 */


interface ListNewsContracts {


    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun showNews(tasks: List<PublicationEntity>)


        fun loaderInitialChargue(active: Boolean)
    }

    interface Presenter : BasePresenter {


        fun loadResult(forceUpdate : Boolean)

    }

}