package pe.gob.onpe.onpedatosabiertos.presentation.news.adapters

import android.content.ClipData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_news.view.*
import pe.gob.onpe.onpedatosabiertos.R
import pe.gob.onpe.onpedatosabiertos.data.entities.PublicationEntity


/**
 * Created by junior on 19/03/18.
 */


class NewsAdapter(val items: ArrayList<PublicationEntity>, val listener: (PublicationEntity) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    var newsItems: List<PublicationEntity> = items
        set(news) {
            field = news
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun getItemCount() = newsItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(newsItems[position], position, listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PublicationEntity, pos: Int, listener: (PublicationEntity) -> Unit) = with(itemView) {


            Glide.with(image_news.context).load(item.image).into(image_news)
            description.text = item.description
            type.text = item.title
            date.text = "10/08/2018"
            itemView.setOnClickListener {
                listener(item)
            }

        }
    }
}
