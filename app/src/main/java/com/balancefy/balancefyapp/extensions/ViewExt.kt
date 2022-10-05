package layout

import android.view.View

fun View.fadeOut(duration: Long = 0) = animate()
    .also { clearAnimation() }
    .setDuration(duration)
    .setStartDelay(1530)
    .alphaBy(-1f)
    .withStartAction{
        alpha = 1f
    }.withEndAction {
        visibility = View.INVISIBLE
    }.start()

fun View.fadeIn(duration: Long = 0, endAction: () -> Unit)= animate()
    .also { clearAnimation() }
    .setDuration(duration)
    .setStartDelay(1550)
    .alpha(1f)
    .withStartAction {
        visibility = View.VISIBLE
        alpha = 0f
    }.withEndAction {
        visibility = View.VISIBLE
        endAction()
    }.start()