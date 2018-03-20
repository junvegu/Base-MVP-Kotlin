package pe.gob.onpe.onpedatosabiertos.base

/**
 * Created by junior on 19/03/18.
 */
interface BaseView<T> {

    var presenter: T

    fun showMessage(msg: String)

    fun loadIndicator(status: Boolean)
}