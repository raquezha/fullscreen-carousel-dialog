package net.raquezha.sampleapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yarolegovich.discretescrollview.DiscreteScrollView
import kotlinx.android.synthetic.main.activity_main.*
import net.raquezha.carouseldialog.CarouselDialog
import net.raquezha.carouseldialog.ImageScaleType
import net.raquezha.carouseldialog.adapters.CarouselAdapter
import net.raquezha.carouseldialog.objects.CarouselData
import net.raquezha.sampleapp.R


class MainActivity : AppCompatActivity() {

    private var carouselDialog: AlertDialog? = null
    private var dataList: MutableList<CarouselData> = mutableListOf()
    private var buttonNames = arrayOf("Button 1", "Button 2", "", "")
    private var imageUrls = arrayOf("https://images.pexels.com/photos/1769329/pexels-photo-1769329.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
            "https://images.pexels.com/photos/277253/pexels-photo-277253.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
            "https://images.pexels.com/photos/1772724/pexels-photo-1772724.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "https://images.pexels.com/photos/1803913/pexels-photo-1803913.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")

    private var placeHolder = R.drawable.ic_placeholder
    private var placeHoldersError = R.drawable.ic_placeholder_error
    private var cardNames = arrayOf("Card 1", "Card 2", "Card 3", "Card 4")
    private var descriptions = arrayOf("Hello There! This is the description of the First Card, Button1. Have a great day folks!",
            "Announcement! Holiday is comming soon, buy now pay later! Come shop with us! Thank you! Image Scale Type is set to 'CENTER_INSIDE'",
            "This is the card 3, hooray for today!", "")
    private var scaleTypes = arrayOf(ImageScaleType.CENTER, ImageScaleType.CENTER, ImageScaleType.CENTER)
    private var cardSize = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataList = generateCarouselData()

        btnShowDialog.setOnClickListener {
            showDialog()
        }
    }

    private fun generateCarouselData(): MutableList<CarouselData> {
        for (i in 0 until cardSize) {
            dataList.add(CarouselData.Builder()
                    .setButtonActionName(buttonNames[i])
                    .setImageUrl(imageUrls[i])
                    .setDescription(descriptions[i])
                    .setPlaceHolderImageRes(placeHolder)
                    .setPlaceHolderErrorImageResId(placeHoldersError)
                    .setCardName(cardNames[i])
                    //.setImageScaleType(scaleTypes[i])
                    //.overrideImageDimension(300)
                    .build()
            )
        }

        return dataList
    }

    fun showDialog() {

        carouselDialog = CarouselDialog.createCarouselDialog(this@MainActivity, dataList) {
            Log.e("MainActivity", "showDialog")
            cancelable = false                  // is your dialog cancellable?
            isInfiniteScrolling = false         // infinite scrolling!
            isSlideOnFling = false              // allow slide through multiple items call
            slideOnFlingThreshold = 2100        // the default threshold is set to 2100. Lower the threshold, more fluid the animation
            offScreenItems = 5                  // Reserve extra space equal to (childSize * count) on each side of the view
            firstItemPosition = 1               // shows the the index when dialog is open
            dismissButtonName = "Close"

            useScaleTransformer(0.9f)  // Transformer

            dismissClickListener {
                Toast.makeText(this@MainActivity, "Dismiss Button!", Toast.LENGTH_SHORT).show()
            }
//            onItemChangedListener(DiscreteScrollView.OnItemChangedListener { _, adapterPosition ->
//                Toast.makeText(this@MainActivity, "OnItemChanged postion $adapterPosition", Toast.LENGTH_SHORT).show()
//            })
//
//            onScrollListener(DiscreteScrollView.ScrollListener { _, _, _, _, _ ->
//                Toast.makeText(this@MainActivity, "OnScroll!!", Toast.LENGTH_SHORT).show()
//            })


            onActionClickListener(object : CarouselAdapter.OnActionClickListener {
                override fun onActionClickListener(carouselData: CarouselData) {
                    Toast.makeText(this@MainActivity, carouselData.accountName, Toast.LENGTH_SHORT).show()
                }
            })
        }

        carouselDialog?.show()

    }

}
