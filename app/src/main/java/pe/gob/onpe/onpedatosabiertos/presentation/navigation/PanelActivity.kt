package pe.gob.onpe.onpedatosabiertos.presentation.navigation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import pe.gob.onpe.onpedatosabiertos.R
import kotlinx.android.synthetic.main.activity_main.*
import pe.gob.onpe.onpedatosabiertos.presentation.news.fragments.ApplicationsFragment
import pe.gob.onpe.onpedatosabiertos.presentation.news.fragments.HelpFragment
import pe.gob.onpe.onpedatosabiertos.presentation.news.fragments.ListNewsFragment
import pe.gob.onpe.onpedatosabiertos.utils.replaceFragmentInActivity


/**
 * Created by junior on 19/03/18.
 */


class PanelActivity : AppCompatActivity() {

    private lateinit var bottomNavitgation: BottomNavigationView

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {



            R.id.navigation_home -> {

                val fragment = ListNewsFragment.Companion.newInstance()
                addFragment(fragment)


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {


                val fragment = ApplicationsFragment.Companion.newInstance()
                addFragment(fragment)


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                val fragment = HelpFragment.Companion.newInstance()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavitgation = findViewById<BottomNavigationView>(R.id.bottomNavitgation)
        bottomNavitgation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.findFragmentById(R.id.body) as ListNewsFragment?
                ?: ListNewsFragment.newInstance().also {

            replaceFragmentInActivity(it, R.id.body)
        }

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.body, fragment, fragment.javaClass.getSimpleName())
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }

}