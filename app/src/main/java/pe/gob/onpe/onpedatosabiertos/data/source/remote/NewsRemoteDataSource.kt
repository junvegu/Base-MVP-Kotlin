package pe.gob.onpe.onpedatosabiertos.data.source.remote

import android.os.Handler
import android.support.annotation.VisibleForTesting
import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity
import pe.gob.onpe.onpedatosabiertos.data.source.datasources.NewsDataSource
import com.google.common.collect.Lists

/**
 * Created by junior on 20/03/18.
 */


class NewsRemoteDataSource private constructor(): NewsDataSource {


    private  val SERVICE_LATENCY_IN_MILLIS = 1000L


    private var NEWS_SERVICE_DATA = LinkedHashMap<String, PublicationEntity>(2)



    //Fake data
    init {
        addNews("Cecile Anges Picture #1 ", "This is a awesome picture by Cecile Agnes", "1", 123123, "https://3.bp.blogspot.com/-sQ2q6pbdChw/UjLO6o5vW-I/AAAAAAAAGd4/FtNll0Q3kJ0/s1600/eser_1_pic_b_by_agnes_cecile-d6ij2lw.jpg",
                "https://3.bp.blogspot.com/-sQ2q6pbdChw/UjLO6o5vW-I/AAAAAAAAGd4/FtNll0Q3kJ0/s1600/eser_1_pic_b_by_agnes_cecile-d6ij2lw.jpg")
        addNews("Cecile Anges Picture #2 ", "This is a awesome picture by Cecile Agnes", "2", 123123, "https://wallup.net/wp-content/uploads/2015/07/women-paintings-eyes-artwork-agnes-cecile.jpg",
                "https://3.bp.blogspot.com/-sQ2q6pbdChw/UjLO6o5vW-I/AAAAAAAAGd4/FtNll0Q3kJ0/s1600/eser_1_pic_b_by_agnes_cecile-d6ij2lw.jpg")
        addNews("Cecile Anges Picture #3 ", "This is a awesome picture by Cecile Agnes", "3", 123123, "http://3.bp.blogspot.com/-H0x5IjrFbpI/U3CDCTcsKkI/AAAAAAAACQ4/QZ7DItcxDoM/s1600/others_voices_by_agnes_cecile-d7ep2ca.jpg",
                "https://3.bp.blogspot.com/-sQ2q6pbdChw/UjLO6o5vW-I/AAAAAAAAGd4/FtNll0Q3kJ0/s1600/eser_1_pic_b_by_agnes_cecile-d6ij2lw.jpg")
        addNews("Cecile Anges Picture #4 ", "This is a awesome picture by Cecile Agnes", "4", 123123, "http://3.bp.blogspot.com/-H0x5IjrFbpI/U3CDCTcsKkI/AAAAAAAACQ4/QZ7DItcxDoM/s1600/others_voices_by_agnes_cecile-d7ep2ca.jpg",
                "https://3.bp.blogspot.com/-sQ2q6pbdChw/UjLO6o5vW-I/AAAAAAAAGd4/FtNll0Q3kJ0/s1600/eser_1_pic_b_by_agnes_cecile-d6ij2lw.jpg")

    }

    private fun addNews(title: String, description: String, id: String, timeStamp: Long, image: String, url: String) {
        val newNews = PublicationEntity(title, description,id,timeStamp,image,url)
        NEWS_SERVICE_DATA.put(newNews.id, newNews)
    }

    override fun getNewsList(callback: NewsDataSource.LoadNewsCallback) {

        //Cargar data de servidor
        val newsList = Lists.newArrayList(NEWS_SERVICE_DATA.values)
        Handler().postDelayed({
            callback.onNewsLoaded(newsList)
        }, SERVICE_LATENCY_IN_MILLIS)

    }

    override fun getNews(newsId: String, callback: NewsDataSource.GetNewsCallback) {
        val news = NEWS_SERVICE_DATA[newsId]

        with(Handler()) {
            if (news != null) {
                postDelayed({ callback.onNewsLoaded(news) }, SERVICE_LATENCY_IN_MILLIS)
            } else {
                postDelayed({ callback.onDataNotAvailable() }, SERVICE_LATENCY_IN_MILLIS)
            }
        }
    }

    override fun refreshNews() {

    }

    override fun deleteAllNews() {
        NEWS_SERVICE_DATA.clear()
    }

    override fun saveNews(news: PublicationEntity) {
        NEWS_SERVICE_DATA.put(news.id, news)
    }


    companion object {

        private lateinit var INSTANCE: NewsRemoteDataSource
        private var needsNewInstance = true

        @JvmStatic fun getInstance(): NewsRemoteDataSource {
            if (needsNewInstance) {
                INSTANCE = NewsRemoteDataSource()
                needsNewInstance = false
            }
            return INSTANCE
        }
    }
}