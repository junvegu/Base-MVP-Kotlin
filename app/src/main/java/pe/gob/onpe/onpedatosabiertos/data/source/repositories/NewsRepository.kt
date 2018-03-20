package pe.gob.onpe.onpedatosabiertos.data.source.repositories

import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity
import pe.gob.onpe.onpedatosabiertos.data.source.datasources.NewsDataSource

/**
 * Created by junior on 20/03/18.
 */


class NewsRepository(val newsRemoteDataSource: NewsDataSource,
                     val newsLocalDataSource: NewsDataSource) : NewsDataSource {


    var cachedNews: LinkedHashMap<String, PublicationEntity> = LinkedHashMap()
    var cacheIsDirty = false


    //DataSource
    override fun getNewsList(callback: NewsDataSource.LoadNewsCallback) {
        if (cachedNews.isNotEmpty() && !cacheIsDirty) {
            callback.onNewsLoaded(ArrayList(cachedNews.values))
            return
        }

        if (cacheIsDirty) {
            getNewsFromRemoteDataSource(callback)
        } else {
            newsLocalDataSource.getNewsList(object : NewsDataSource.LoadNewsCallback {
                override fun onNewsLoaded(tasks: List<PublicationEntity>) {
                    refreshCache(tasks)
                    callback.onNewsLoaded(ArrayList(cachedNews.values))
                }

                override fun onDataNotAvailable() {
                    getNewsFromRemoteDataSource(callback)
                }
            })
        }


    }

    private fun getNewsFromRemoteDataSource(callback: NewsDataSource.LoadNewsCallback) {
        newsRemoteDataSource.getNewsList(object : NewsDataSource.LoadNewsCallback {
            override fun onNewsLoaded(tasks: List<PublicationEntity>) {
                refreshCache(tasks)
                refreshLocalDataSource(tasks)
                callback.onNewsLoaded(ArrayList(cachedNews.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }


    private fun refreshLocalDataSource(newsList: List<PublicationEntity>) {
        newsLocalDataSource.deleteAllNews()
        for (news in newsList) {
            newsLocalDataSource.saveNews(news)
        }
    }

    override fun getNews(taskId: String, callback: NewsDataSource.GetNewsCallback) {
        val taskInCache = getNewsById(taskId)


        if (taskInCache != null) {
            callback.onNewsLoaded(taskInCache)
            return
        }

        newsLocalDataSource.getNews(taskId, object : NewsDataSource.GetNewsCallback {
            override fun onNewsLoaded(task: PublicationEntity) {
                cacheAndPerform(task) {
                    callback.onNewsLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                newsRemoteDataSource.getNews(taskId, object : NewsDataSource.GetNewsCallback {
                    override fun onNewsLoaded(task: PublicationEntity) {
                        cacheAndPerform(task) {
                            callback.onNewsLoaded(it)
                        }
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
            }
        })
    }

    private fun getNewsById(id: String) = cachedNews[id]

    override fun refreshNews() {
        cacheIsDirty = false
    }

    override fun deleteAllNews() {
        newsRemoteDataSource.deleteAllNews()
        newsLocalDataSource.deleteAllNews()
        cachedNews.clear()
    }

    override fun saveNews(news: PublicationEntity) {
        cacheAndPerform(news) {
            newsRemoteDataSource.saveNews(it)
            newsLocalDataSource.saveNews(it)
        }

    }


    //Private
    private fun refreshCache(tasks: List<PublicationEntity>) {
        cachedNews.clear()
        tasks.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private inline fun cacheAndPerform(news: PublicationEntity, perform: (PublicationEntity) -> Unit) {
        val cachedNews = PublicationEntity(news.title, news.description, news.id, news.timesTamp, news.image,
                news.url).apply {
            type = news.type
        }
        this.cachedNews.put(cachedNews.id, cachedNews)
        perform(cachedNews)
    }

    companion object {

        private var INSTANCE: NewsRepository? = null

        //Conservar instancia única - singleton
        @JvmStatic fun getInstance(tasksRemoteDataSource: NewsDataSource,
                        tasksLocalDataSource: NewsDataSource): NewsRepository {
            return INSTANCE ?: NewsRepository(tasksRemoteDataSource, tasksLocalDataSource)
                    .apply { INSTANCE = this }
        }



        //forzar creación de nueva instancia
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

}