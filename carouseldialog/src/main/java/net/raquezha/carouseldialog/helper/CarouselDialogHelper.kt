package net.raquezha.carouseldialog.helper

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.DiscreteScrollView
import net.raquezha.carouseldialog.adapters.CarouselAdapter
import net.raquezha.carouseldialog.base.BaseDialogHelper
import net.raquezha.carouseldialog.objects.CarouselData
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import net.raquezha.carouseldialog.R


class CarouselDialogHelper(private var context: Context, private var carouselData: MutableList<CarouselData>) : BaseDialogHelper() {

    var isInfiniteScrolling = false
    var isSlideOnFling = false
    var slideOnFlingThreshold = 2100
    var itemTransitionTimeMillis = 150
    var offScreenItems = 1
    var firstItemPosition = 0
    var dismissButtonName = "DISMISS"
    var dismissButtonTextColor = ContextCompat.getColor(context, R.color.white)

    private var useDissmisBackground = true
    private var dismissButtonBackgroundColor = ContextCompat.getColor(context, R.color.orange_red)
    private var dismissButtonBackground = ContextCompat.getDrawable(context, R.drawable.dismiss_button_background)

    private var carouselAdapter: CarouselAdapter? = null

    var useScaleTransformer = false

    private var minScale = 1f
    private var maxScale = 1f
    private var pivotX = Pivot.X.CENTER
    private var pivotY = Pivot.Y.BOTTOM

    private lateinit var evaluator: ArgbEvaluator

    fun getAdapter(): CarouselAdapter {
        if (carouselAdapter == null)
            carouselAdapter = CarouselAdapter(carouselData)
        return carouselAdapter!!
    }

    override val builder: AlertDialog.Builder
        get() = AlertDialog.Builder(context, R.style.DialogTheme).setView(dialogView)

    //  dialog view
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.carousel_dialog, null)
    }

    // dismiss button
    private val dismissButton: AppCompatButton by lazy {
        dialogView.findViewById<AppCompatButton>(R.id.btnDismiss)
    }

    // discrete scrollview
    private val discreteScrollView: DiscreteScrollView by lazy {
        dialogView.findViewById<DiscreteScrollView>(R.id.discreteScrollview)
    }

    fun useScaleTransformer(minScale: Float = 1f, maxScale: Float = 1f, pivotX: Pivot.X = Pivot.X.CENTER, pivotY: Pivot.Y = Pivot.Y.BOTTOM) {
        useScaleTransformer = true
        this.minScale = minScale
        this.maxScale = maxScale
        this.pivotX = pivotX
        this.pivotY = pivotY
    }

    fun setDismissButtonBackground(background: Drawable) {
        useDissmisBackground = true
        dismissButtonBackground = background
    }

    fun setDismissButtonBackgroundColor(color: Int) {
        useDissmisBackground = false
        dismissButtonBackgroundColor = color
    }

    override fun initializeDialog() {

        dismissButton.text = dismissButtonName
        dismissButton.setTextColor(dismissButtonTextColor)

        if (useDissmisBackground)
            dismissButton.background = dismissButtonBackground
        else
            dismissButton.setBackgroundColor(dismissButtonBackgroundColor)


        evaluator = ArgbEvaluator()

        if (isInfiniteScrolling) {
            val wrapper = InfiniteScrollAdapter.wrap(getAdapter())
            discreteScrollView.adapter = wrapper
        } else
            discreteScrollView.adapter = getAdapter()

        discreteScrollView.setSlideOnFling(isSlideOnFling)
        discreteScrollView.setSlideOnFlingThreshold(slideOnFlingThreshold)

        discreteScrollView.setOrientation(DSVOrientation.HORIZONTAL)
        discreteScrollView.setItemTransitionTimeMillis(itemTransitionTimeMillis)
        discreteScrollView.setOffscreenItems(offScreenItems)

        if (useScaleTransformer)
            discreteScrollView.setItemTransformer(ScaleTransformer.Builder()
                    .setMinScale(minScale)
                    .setMaxScale(maxScale)
                    .setPivotX(pivotX)
                    .setPivotY(pivotY)
                    .build())

        discreteScrollView.scrollToPosition(firstItemPosition)
    }

    fun onActionClickListener(onActionClickListener: CarouselAdapter.OnActionClickListener) {
        getAdapter().onActionClickListener = onActionClickListener
    }

    fun onScrollListener(scrollListener: DiscreteScrollView.ScrollListener<CarouselAdapter.ViewHolder>) {
        discreteScrollView.addScrollListener(scrollListener)
    }

    fun onItemChangedListener(onItemChangedListener: DiscreteScrollView.OnItemChangedListener<CarouselAdapter.ViewHolder>) {
        discreteScrollView.addOnItemChangedListener(onItemChangedListener)
    }

    fun dismissClickListener(func: (() -> Unit)? = null) =
            with(dismissButton) {
                onDismissDialogListener(func)
            }
}