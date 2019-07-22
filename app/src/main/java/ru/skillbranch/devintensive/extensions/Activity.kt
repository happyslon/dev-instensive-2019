package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val activityRootView = this.window.decorView
    val r = Rect()
    //r will be populated with the coordinates of your view that area still visible.
    activityRootView.getWindowVisibleDisplayFrame(r)

    val heightDiff = activityRootView.rootView.height - (r.bottom - r.top)
    return heightDiff > 100
}

fun Activity.isKeyboardClosed(): Boolean {
    val activityRootView = this.window.decorView
    val r = Rect()
    //r will be populated with the coordinates of your view that area still visible.
    activityRootView.getWindowVisibleDisplayFrame(r)

    val heightDiff = activityRootView.rootView.height - (r.bottom - r.top)
    return heightDiff < 100
}