package com.devpraskov.skyengdictionary.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun EditText?.debounceAfterTextChanged(
    coroutineScope: CoroutineScope,
    onTextChanged: (String) -> Unit
) {
    this?.addTextChangedListener(object : TextWatcher {
        var debouncePeriod: Long = 400
        private var searchJob: Job? = null
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                text?.toString()?.let { nextText ->
                    delay(debouncePeriod)
                    onTextChanged.invoke(nextText)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    })
}

fun EditText?.enterClick(block: (String) -> Unit) {
    this?.setOnEditorActionListener { v, actionId, event ->
        if ((event == null || event.action == KeyEvent.ACTION_UP) && actionId == EditorInfo.IME_ACTION_DONE) {
            block.invoke(this.text.toString())
            true
        } else false
    }
}


fun Activity.statusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }
}

fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.gone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}


/**
 * Hide with alpha animation the view. (visibility = View.INVISIBLE)
 */
fun View.hideAnimateAlpha(duration: Long = 200): View {
    if (visibility == View.VISIBLE) {
        animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction {
                visibility = View.GONE
            }
            .start()
    }
    return this
}

/**
 * Show with alpha animation the view. (visibility = View.INVISIBLE)
 */
fun View.showAnimateAlpha(duration: Long = 200): View {
    if (visibility in listOf(View.INVISIBLE, View.GONE)) {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
            .start()
    }
    return this
}

fun <T : View?> T?.onClick(block: (T?) -> Unit) {
    this?.setOnClickListener { block(it as T) }
}


fun Activity.hideSoftKeyboard(view: View? = null) {
    if (view != null) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

fun View?.visibility(isVisible: Boolean) {
    this?.apply { visibility = if (isVisible) View.VISIBLE else View.INVISIBLE }
}

fun View?.visibilityGone(isVisible: Boolean) {
    this?.apply { visibility = if (isVisible) View.VISIBLE else View.GONE }
}


fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)