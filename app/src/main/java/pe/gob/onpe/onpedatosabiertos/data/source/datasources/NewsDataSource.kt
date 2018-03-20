package pe.gob.onpe.onpedatosabiertos.data.source.datasources

import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity

/**
 * Created by junior on 20/03/18.
 */

interface NewsDataSource {


    interface LoadNewsCallback {

        fun onNewsLoaded(tasks: List<PublicationEntity>)

        fun onDataNotAvailable()
    }


    interface GetNewsCallback {

        fun onNewsLoaded(task: PublicationEntity)

        fun onDataNotAvailable()

    }

    fun getNewsList(callback: LoadNewsCallback)

    fun getNews(taskId: String, callback: GetNewsCallback)

    fun refreshNews()

    fun deleteAllNews()

    fun saveNews(news: PublicationEntity)

}