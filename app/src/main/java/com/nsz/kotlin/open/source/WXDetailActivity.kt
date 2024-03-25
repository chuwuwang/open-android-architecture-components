package com.nsz.kotlin.open.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nsz.kotlin.R
import com.nsz.kotlin.databinding.ItemWxAuthorLvBinding


class WXDetailActivity : AppCompatActivity() {

    private val dataList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source_wx_detail)
        initView()
    }

    private fun initView() {
        dataList.add(R.drawable.ic_author)
        dataList.add(R.drawable.profile_1)
        dataList.add(R.drawable.profile_46)
        dataList.add(R.drawable.profile_2)
        dataList.add(R.drawable.profile_22)
        dataList.add(R.drawable.profile_3)
        dataList.add(R.drawable.profile_23)
        dataList.add(R.drawable.profile_4)
        dataList.add(R.drawable.profile_24)
        dataList.add(R.drawable.profile_5)
        dataList.add(R.drawable.profile_25)
        dataList.add(R.drawable.profile_21)
        dataList.add(R.drawable.profile_6)
        dataList.add(R.drawable.profile_36)
        dataList.add(R.drawable.profile_7)
        dataList.add(R.drawable.profile_37)
        dataList.add(R.drawable.profile_8)
        dataList.add(R.drawable.profile_38)
        dataList.add(R.drawable.profile_9)
        dataList.add(R.drawable.profile_39)
        dataList.add(R.drawable.profile_10)
        dataList.add(R.drawable.profile_40)
        dataList.add(R.drawable.profile_11)
        dataList.add(R.drawable.profile_41)
        dataList.add(R.drawable.profile_12)
        dataList.add(R.drawable.profile_42)
        dataList.add(R.drawable.profile_13)
        dataList.add(R.drawable.profile_43)
        dataList.add(R.drawable.profile_14)
        dataList.add(R.drawable.profile_44)
        dataList.add(R.drawable.profile_15)
        dataList.add(R.drawable.profile_45)
        dataList.add(R.drawable.profile_16)
        dataList.add(R.drawable.profile_17)
        dataList.add(R.drawable.profile_47)
        dataList.add(R.drawable.profile_18)
        dataList.add(R.drawable.profile_48)
        dataList.add(R.drawable.profile_19)
        dataList.add(R.drawable.profile_49)
        dataList.add(R.drawable.profile_20)
        dataList.add(R.drawable.profile_30)
        dataList.add(R.drawable.profile_31)
        dataList.add(R.drawable.profile_32)
        dataList.add(R.drawable.profile_33)
        dataList.add(R.drawable.profile_34)
        dataList.add(R.drawable.profile_26)
        dataList.add(R.drawable.profile_27)
        dataList.add(R.drawable.profile_28)
        dataList.add(R.drawable.profile_29)
        dataList.add(R.drawable.profile_35)

        val layoutManager = GridLayoutManager(this, 9)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter = AuthorAdapter()
    }

    inner class AuthorAdapter : RecyclerView.Adapter<WXDetailActivity.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWxAuthorLvBinding.inflate(layoutInflater)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataList[position]
            holder.bind(item, position)
        }

    }

    inner class ViewHolder(private var binding: ItemWxAuthorLvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resId: Int, position: Int) {
            binding.authorImage.setImageResource(resId)
        }

    }

}