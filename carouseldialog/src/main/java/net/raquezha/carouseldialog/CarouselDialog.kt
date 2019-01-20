package net.raquezha.carouseldialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import net.raquezha.carouseldialog.helper.CarouselDialogHelper
import net.raquezha.carouseldialog.objects.CarouselData

object CarouselDialog{
    inline fun createCarouselDialog(context: Context, carouselData: MutableList<CarouselData>, func: CarouselDialogHelper.() -> Unit): AlertDialog =
            CarouselDialogHelper(context, carouselData ).apply {
                func()
            }.create()
}