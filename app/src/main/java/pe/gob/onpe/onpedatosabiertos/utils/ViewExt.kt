package pe.gob.onpe.onpedatosabiertos.utils

/**
 * Created by junior on 19/03/18.
 */
import android.support.design.widget.Snackbar
import android.view.View

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}