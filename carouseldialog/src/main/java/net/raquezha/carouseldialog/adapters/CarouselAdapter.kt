package net.raquezha.carouseldialog.adapters

import android.app.Activity
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.raquezha.carouseldialog.R
import net.raquezha.carouseldialog.objects.CarouselData
import android.content.Context
import android.widget.Space
import com.bumptech.glide.request.RequestOptions
import net.raquezha.carouseldialog.ImageScaleType


class CarouselAdapter constructor(private var data: MutableList<CarouselData>) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    private var itemHeight: Int = 0
    private lateinit var context: Context
    var onActionClickListener: OnActionClickListener? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
        val context = recyclerView.context as Activity
        val windowDimensions = Point()
        context.windowManager.defaultDisplay.getSize(windowDimensions)
        itemHeight = Math.round(windowDimensions.y * 0.8f)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_carousel, parent, false)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight)
        v.layoutParams = params
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
                .applyDefaultRequestOptions(generateRequestOptions(data[position].imageScaleType, position))
                .load(data[position].imageUrl)
                .into(holder.image)

        if (data[position].description?.isEmpty()!!) {
            holder.description.visibility = View.GONE
            holder.space .visibility = View.GONE
        } else
            holder.description.text = data[position].description

        if (data[position].actionType <= 0) {
            holder.actionName.visibility = View.GONE
        } else {
            holder.actionName.text = data[position].actionName
            holder.actionName.setOnClickListener { onActionClickListener?.onActionClickListener(data[position]) }
        }
    }

    private fun generateRequestOptions(scaleType: ImageScaleType, position: Int): RequestOptions {
        val requestOptions = RequestOptions()
                .placeholder(data[position].placeHolderImageResId)
                .error(data[position].placeHolderErrorImageResId)

        when (scaleType) {
            ImageScaleType.CENTER_CROP -> {
                requestOptions.centerCrop()
            }
            ImageScaleType.CENTER_INSIDE -> {
                requestOptions.centerInside()
            }
            ImageScaleType.FIT_CENTER -> {
                requestOptions.fitCenter()
            }
            else -> {
                // center nothing default
            }

        }

        val height = data[position].imageHeight
        val width = data[position].imageWidth

        if (height != 0 && width != 0)
            requestOptions.override(height, width).centerCrop()


        return requestOptions
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: AppCompatImageView = itemView.findViewById(R.id.ivImage)
        val description: AppCompatTextView = itemView.findViewById(R.id.tvDesription)
        val actionName: AppCompatTextView = itemView.findViewById(R.id.tvActionName)
        val space: Space = itemView.findViewById(R.id.space)
    }

    interface OnActionClickListener {
        fun onActionClickListener(carouselData: CarouselData)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

