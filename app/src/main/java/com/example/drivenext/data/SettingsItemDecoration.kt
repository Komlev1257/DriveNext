package com.example.drivenext.data

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SettingsItemDecoration(
    private val spacingSmall: Int,
    private val spacingLarge: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        // Профиль
        if (position == 0) {
            outRect.bottom = spacingSmall
        } else {
            // После конкретных пунктов отступ
            if (position == 1 || position == 4) {
                outRect.bottom = spacingLarge
            } else {
                outRect.bottom = spacingSmall
            }
        }
    }
}
