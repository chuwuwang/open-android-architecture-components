package com.nsz.kotlin.aac.ui.layout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nsz.kotlin.R
import com.nsz.kotlin.databinding.ActivityAccUiPager2VerticalBinding

class ViewPager2VerticalActivity : AppCompatActivity() {

    private val binding: ActivityAccUiPager2VerticalBinding by lazy { ActivityAccUiPager2VerticalBinding.inflate(layoutInflater) }
    private val list = mutableListOf("#FFFF00", "#FF0000", "#AAFF00", "#FF44FF", "#EEFFEE", "#EEE000")

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        list.add("")
        val pagerAdapter = PagerAdapter()
        binding.viewPager2.adapter = pagerAdapter
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rb_vertical -> binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
                R.id.rb_horizontal -> binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }
        // 禁止滑动
        binding.cbIsUserInputEnabled.setOnCheckedChangeListener { _, b ->
            binding.viewPager2.isUserInputEnabled = b
        }
    }

    inner class PagerAdapter : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = View.inflate(parent.context, R.layout.item_acc_ui_pager2_vertical, null)
            view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val color = list[position]
            if (color !== "") {
                val parseColor = Color.parseColor(color)
                holder.view.setBackgroundColor(parseColor)
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val view: View = itemView.findViewById(R.id.view_content)
        }

    }

}