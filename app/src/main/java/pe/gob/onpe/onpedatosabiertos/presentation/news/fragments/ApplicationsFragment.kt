package pe.gob.onpe.onpedatosabiertos.presentation.news.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pe.gob.onpe.onpedatosabiertos.R

/**
 * Created by junior on 19/03/18.
 */

class ApplicationsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_list_news, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()
    }


    companion object {

        fun newInstance(): ApplicationsFragment {
            return ApplicationsFragment()
        }
    }
}