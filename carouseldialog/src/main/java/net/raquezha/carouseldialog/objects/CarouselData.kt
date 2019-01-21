package net.raquezha.carouseldialog.objects

import net.raquezha.carouseldialog.ImageScaleType
import net.raquezha.carouseldialog.R

class CarouselData() {
    var placeHolderImageResId: Int = -1
    var placeHolderErrorImageResId = -1
    var imageUrl: String? = null
    var description: String? = null
    var actionName: String? = null
    var accountName: String? = null
    var imageScaleType = ImageScaleType.CENTER
    var actionType: Int = -1
    var imageHeight = 0
    var imageWidth = 0

    constructor(builder: Builder) : this() {
        this.placeHolderImageResId = builder.getPlaceHolderImageResId()
        this.imageUrl = builder.getImageUrl()
        this.description = builder.getDescription()
        this.actionType = builder.getActionType()
        this.actionName = builder.getActionName()
        this.imageScaleType = builder.getImageScaleType()
        this.accountName = builder.getAccountName()
        this.placeHolderErrorImageResId = builder.getPlaceHolderErrorImageResId()
        this.imageHeight = builder.getImageHeight()
        this.imageWidth = builder.getImageWidth()
    }

    class Builder {
        private var placeHolderImageResId: Int = R.drawable.ic_placeholder
        private var placeHolderErrorImageResId: Int = R.drawable.ic_placeholder_error
        private var imageUrl: String? = null
        private var description: String? = ""
        private var actionType: Int = -1
        private var actionName: String? = null
        private var imageScaleType = ImageScaleType.CENTER
        private var cardName: String? = null
        private var imageHeight = 0
        private var imageWidth = 0

        fun build(): CarouselData {
            return CarouselData(this)
        }

        fun setCardName(cardName: String): Builder {
            this.cardName = cardName
            return this
        }

        fun overrideImageDimension(size: Int): Builder {
            return overrideImageDimension(size, size)
        }

        fun overrideImageDimension(height:Int, width: Int): Builder {
            this.imageHeight = height
            this.imageWidth = width
            return this
        }

        fun setImageScaleType(scaleType: ImageScaleType): Builder {
            this.imageScaleType = scaleType
            return this
        }

        fun setPlaceHolderImageRes(resId: Int): Builder {
            this.placeHolderImageResId = resId
            return this
        }

        fun setPlaceHolderErrorImageResId(resId: Int): Builder {
            this.placeHolderErrorImageResId = resId
            return this
        }

        fun setImageUrl(url: String): Builder {
            this.imageUrl = url
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setButtonActionName(actionName: String): Builder {
            this.actionName = actionName
            this.actionType = if (actionName.isEmpty()) -1 else 1
            return this
        }

        fun getAccountName(): String? {
            return cardName
        }

        fun getImageScaleType(): ImageScaleType {
            return imageScaleType
        }

        fun getPlaceHolderImageResId(): Int {
            return placeHolderImageResId
        }

        fun getPlaceHolderErrorImageResId(): Int {
            return placeHolderImageResId
        }

        fun getImageUrl(): String? {
            return imageUrl
        }

        fun getDescription(): String? {
            return description
        }

        fun getActionName(): String? {
            return actionName
        }

        fun getActionType(): Int {
            return actionType
        }

        fun getImageHeight(): Int {
            return imageHeight
        }

        fun getImageWidth(): Int {
            return imageWidth
        }

    }
}