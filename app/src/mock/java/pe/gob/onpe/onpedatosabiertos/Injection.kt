package pe.gob.onpe.onpedatosabiertos

import android.content.Context
import pe.gob.onpe.onpedatosabiertos.data.source.local.NewsLocalDataSource
import pe.gob.onpe.onpedatosabiertos.data.source.remote.NewsRemoteDataSource
import pe.gob.onpe.onpedatosabiertos.data.source.repositories.NewsRepository

/**
 * Created by junior on 20/03/18.
 */


//Crear instancia para mock desde un orígen de datos de prueba


object Injection {
    fun provideNewsRepository(context: Context?): NewsRepository {

        return NewsRepository.getInstance(NewsRemoteDataSource.getInstance(),
                NewsLocalDataSource.getInstance())
    }
}