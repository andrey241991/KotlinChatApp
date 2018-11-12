package com.example.home_pc.kotlinchatapp

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerItemDecorator: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = 20
        outRect.right = 20
        outRect.left = 20
    }
}