package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import com.example.myapplication.adapters.Adapter

class MyItemDecoration (context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint()
    private val height: Int

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.argb((255 * 0.2).toInt(), 0, 0, 0)
        height =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, context.resources.displayMetrics)
                .toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val viewType = parent.adapter!!.getItemViewType(position)
        if (viewType == Adapter.TYPE_ITEM && hasDividerOnBottom(view, parent, state))
            outRect.set(0, 0, 0, height)
        else
            outRect.setEmpty()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val viewType = parent.adapter!!.getItemViewType(position)
            if (viewType == Adapter.TYPE_ITEM && hasDividerOnBottom(view, parent, state)) {
                c.drawRect(
                    view.left.toFloat(),
                    view.bottom.toFloat(),
                    view.right.toFloat(),
                    (view.bottom + height).toFloat(),
                    paint
                )
            }
        }
    }

    private fun hasDividerOnBottom(view: View, parent: RecyclerView, state: RecyclerView.State): Boolean {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        return (position < state.itemCount && parent.adapter!!.getItemViewType(position) != Adapter.TYPE_ITEM
                && parent.adapter!!.getItemViewType(position + 1) != Adapter.TYPE_ITEM)
    }
}