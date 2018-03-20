package pe.gob.onpe.onpedatosabiertos.data.source.local

import android.support.annotation.VisibleForTesting
import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity
import pe.gob.onpe.onpedatosabiertos.data.source.datasources.NewsDataSource
import pe.gob.onpe.onpedatosabiertos.data.source.remote.NewsRemoteDataSource

/**
 * Created by junior on 20/03/18.
 */


class NewsLocalDataSource private constructor() : NewsDataSource {


    override fun getNewsList(callback: NewsDataSource.LoadNewsCallback) {

       //Implementar llamada a bd local
        callback.onDataNotAvailable()
    }

    override fun getNews(taskId: String, callback: NewsDataSource.GetNewsCallback) {

    }

    override fun refreshNews() {

    }

    override fun deleteAllNews() {

    }

    override fun saveNews(news: PublicationEntity) {

    }


    companion object {
        private var INSTANCE: NewsLocalDataSource? = null

        @JvmStatic
        fun getInstance(): NewsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(NewsLocalDataSource::javaClass) {
                    INSTANCE = NewsLocalDataSource()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}