# Fullscreen Carousel Dialog
A Library that loads your image, descriptions & button in a carousel style dialog

[ ![Download](https://api.bintray.com/packages/raquezha/fullscreen-carousel-dialog/carouseldialog/images/download.svg) ](https://bintray.com/raquezha/fullscreen-carousel-dialog/carouseldialog/_latestVersion)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)

![](sample.gif)

## Gradle
```groovy
dependencies {
  implementation 'net.raquezha.carouseldialog:carouseldialog:1.0.0-rc2'
}
```

## Usage
1. You need to populate a list with [CarouselData]( https://github.com/raquezha/fullscreen-carousel-dialog/tree/master/carouseldialog/src/main/java/net/raquezha/carouseldialog/objects ). Use `CarouselData.Builder()` when creating an CarouselData object: 
```kotlin
  private var dataList: MutableList<CarouselData> = mutableListOf()
  private var buttonNames = arrayOf("Button 1", "Button 2", "", "")
  private var cardSize =  3
   
  private fun generateCarouselData(): MutableList<CarouselData> {
        for (i in 0 until cardSize) {
            dataList.add(CarouselData.Builder()
                    .setButtonActionName("OK") // optional. this will be the name of the button of each item. leave this blank then it will hide the button
                    .setImageUrl( "https://images.pexels.com/photos/277253/pexels-photo-277253.jpeg) // I'm using Glide to load image from url. 
                    .setDescription("Description $i") // this is the description placed under the image
                    .setPlaceHolderImageRes(R.drawable.ic_placeholder) // default image while loading the image
                    .setPlaceHolderErrorImageResId( R.drawable.ic_placeholder_error) // error image when downloading failed
                    .setCardName("Card $i") // if you want to name the item, this will be return via callback
                    .build()
            )
        }

        return dataList
    }
  
```

2. create a carousel dialog:
```kotlin
CarouselDialog.createCarouselDialog(context, dataList) {
// customize your dialog here
}
```
just wrap it inside separate function let's say `fun showDialog()`
```kotlin
private var carouselDialog: AlertDialog? = null
fun showDialog() {
        carouselDialog = CarouselDialog.createCarouselDialog(this@MainActivity, dataList) {
            Log.e("MainActivity", "showDialog")
            cancelable = false                  // is your dialog cancellable?
            isInfiniteScrolling = false         // infinite scrolling!
            isSlideOnFling = false              // allow slide through multiple items call
            slideOnFlingThreshold = 2100        // the default threshold is set to 2100. Lower the threshold, more fluid the animation
            offScreenItems = 5                  // Reserve extra space equal to (childSize * count) on each side of the view
            firstItemPosition = 1               // shows the the index when dialog is open
            dismissButtonName = "Close"         // the name of the dialog under list of carousel items

            useScaleTransformer(0.9f)  // Transformer

            dismissClickListener {
                Toast.makeText(this@MainActivity, "Dismiss Button!", Toast.LENGTH_SHORT).show()
            }
            onItemChangedListener(DiscreteScrollView.OnItemChangedListener { _, adapterPosition ->
                Toast.makeText(this@MainActivity, "OnItemChanged postion $adapterPosition", Toast.LENGTH_SHORT).show()
            })

            onScrollListener(DiscreteScrollView.ScrollListener { _, _, _, _, _ ->
                Toast.makeText(this@MainActivity, "OnScroll!!", Toast.LENGTH_SHORT).show()
            })
            
            // this will return a carouselData when you click the action button.
            onActionClickListener(object : CarouselAdapter.OnActionClickListener {
                override fun onActionClickListener(carouselData: CarouselData) {
                    Toast.makeText(this@MainActivity, carouselData.accountName, Toast.LENGTH_SHORT).show()
                }
            })
        }
        // show the dialog
        carouselDialog?.show()
    }
```

That's it.

## Sample
Please see the sample [app](https://github.com/raquezha/fullscreen-carousel-dialog/blob/master/app/src/main/java/net/raquezha/sampleapp/MainActivity.kt) for examples of library usage.


## Contributing
To contribute, clone this repo locally and commit your code on a separate branch.Then submit a pull-request.

This project is using AndroidX on Android Studio 3.3
Written in Kotlin 1.3.11


## Thanks
Thanks to [@yarolegovich]( https://github.com/yarolegovich )'s  [Discrete ScrollView]( https://github.com/yarolegovich/DiscreteScrollView ) Library 
  

Thanks to [@Mohammed Audhil]( https://android.jlelse.eu/@audhilmohammed )'s [Custom Alert Dialog Tutorial](  https://android.jlelse.eu/android-custom-alert-dialogs-kotlin-extension-functions-kotlin-higher-order-functions-life-682305c5322e )

## License
```
Copyright 2019 raquezha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
