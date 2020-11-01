package kz.mentalmind.utils

import android.util.DisplayMetrics
import android.view.View

fun View.dpToPixelInt(dp: Float): Int =
    (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()