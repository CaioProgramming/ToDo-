package com.myself.todo.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mikhaellopez.rxanimation.fadeIn
import com.myself.todo.R
import com.myself.todo.Utils.Utilities
import com.myself.todo.databinding.CardlayoutfotosBinding
import com.myself.todo.model.beans.Album
import com.myself.todo.view.activities.NewPicActivity
import com.myself.todo.view.alerts.FotoAlert
import java.util.*

class RecyclerFotoAdapter(val activity: Activity,val albumlist: ArrayList<Album>) : RecyclerView.Adapter<RecyclerFotoAdapter.PicturesViewHolder>() {

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val album = albumlist[position]
        val cardlayoutfotosBinding = holder.cardlayoutbind
        val repeatanimation = AnimationUtils.loadAnimation(activity,R.anim.fade_in_repeat)
        cardlayoutfotosBinding.mainshimmer.startAnimation(repeatanimation)
        if (!album.isAcreatePicture()) {
            Glide.with(activity).load(album.fotouri).addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    cardlayoutfotosBinding.albpic.visibility = GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    cardlayoutfotosBinding.mainshimmer.stopShimmer()
                    cardlayoutfotosBinding.mainshimmer.clearAnimation()
                    cardlayoutfotosBinding.mainshimmer.fadeIn()
                    return false
                }
            }).into(cardlayoutfotosBinding.albpic)
            cardlayoutfotosBinding.albcard.setOnClickListener { FotoAlert(activity, albumlist, position) }
        }else{
            Glide.with(activity).load(R.drawable.ic_camera_shadowed).into(cardlayoutfotosBinding.albpic)
            cardlayoutfotosBinding.albcard.setOnClickListener { addNewPic()}
        }
        cardlayoutfotosBinding.albcard.fadeIn().subscribe()
        cardlayoutfotosBinding.diapic.text = Utilities.convertDate(album.dia)
        val handler = Handler()
        handler.postDelayed({
            cardlayoutfotosBinding.mainshimmer.hideShimmer()
            cardlayoutfotosBinding.mainshimmer.clearAnimation()
        }, 1500)
    }

    private fun addNewPic() {
        val i = Intent(activity, NewPicActivity::class.java)
        activity.startActivity(i)
    }


    override fun getItemCount(): Int {
       return albumlist.size
    }

    class PicturesViewHolder(val cardlayoutbind: CardlayoutfotosBinding) : RecyclerView.ViewHolder(cardlayoutbind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val cardlayoutfotosBinding:CardlayoutfotosBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.cardlayoutfotos,parent,false)
        return  PicturesViewHolder(cardlayoutfotosBinding)
    }


}