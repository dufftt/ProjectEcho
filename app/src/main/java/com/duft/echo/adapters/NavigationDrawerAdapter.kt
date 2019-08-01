package com.duft.echo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duft.echo.R
import com.duft.echo.activities.MainActivity
import com.duft.echo.fragments.AboutUsFragment
import com.duft.echo.fragments.FavouriteFragment
import com.duft.echo.fragments.MainFragment
import com.duft.echo.fragments.SettingsFragment

class NavigationDrawerAdapter(_contentList: ArrayList<String>, _getImages: IntArray, _context: Context) :
    RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>() {
    override fun getItemCount(): Int {
        return contentList?.size as Int
    }

    var contentList: ArrayList<String>? = null
    var getImages: IntArray? = null
    var mContext: Context? = null

    init {
        this.contentList = _contentList
        this.getImages = _getImages
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_custom_navigationdrawer, parent, false)
        val returnThis = NavViewHolder(itemView)
        return returnThis

    }


    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder?.icon_GET?.setBackgroundResource(getImages?.get(position) as Int)
        holder?.text_GET?.setText(contentList?.get(position))
        holder?.contentholder?.setOnClickListener({
            if (position == 0) {
                val mainScreenfragment = MainFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, mainScreenfragment)
                    .commit()
            } else if (position == 1) {
                val favouritefragment = FavouriteFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, favouritefragment)
                    .commit()
            } else if (position == 2) {
                val Settingsfragment = SettingsFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, Settingsfragment)
                    .commit()
            } else {
                val AboutUsfragment = AboutUsFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, AboutUsfragment)
                    .commit()


            }
        })
    }

    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var icon_GET: ImageView? = null
        var text_GET: TextView? = null
        var contentholder: RelativeLayout? = null

        init {
            icon_GET = itemView?.findViewById(R.id.icon_navdrawer)
            text_GET = itemView?.findViewById(R.id.text_navdrawer)
            contentholder = itemView?.findViewById(R.id.navdrawer_item_content_holder)
        }


    }

}