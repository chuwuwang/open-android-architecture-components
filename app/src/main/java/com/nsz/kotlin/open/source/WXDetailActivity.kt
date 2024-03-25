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
        dataList.add(R.drawable.profile_60)
        dataList.add(R.drawable.profile_61)
        dataList.add(R.drawable.profile_62)
        dataList.add(R.drawable.profile_63)
        dataList.add(R.drawable.profile_64)
        dataList.add(R.drawable.profile_65)
        dataList.add(R.drawable.profile_66)
        dataList.add(R.drawable.profile_67)
        dataList.add(R.drawable.profile_68)
        dataList.add(R.drawable.profile_69)
        dataList.add(R.drawable.profile_70)
        dataList.add(R.drawable.profile_71)
        dataList.add(R.drawable.profile_72)
        dataList.add(R.drawable.profile_73)
        dataList.add(R.drawable.profile_74)
        dataList.add(R.drawable.profile_75)
        dataList.add(R.drawable.profile_76)
        dataList.add(R.drawable.profile_77)
        dataList.add(R.drawable.profile_78)
        dataList.add(R.drawable.profile_79)
        dataList.add(R.drawable.profile_80)
        dataList.add(R.drawable.profile_81)
        dataList.add(R.drawable.profile_82)
        dataList.add(R.drawable.profile_83)
        dataList.add(R.drawable.profile_84)
        dataList.add(R.drawable.profile_85)
        dataList.add(R.drawable.profile_86)
        dataList.add(R.drawable.profile_87)
        dataList.add(R.drawable.profile_88)
        dataList.add(R.drawable.profile_89)
        dataList.add(R.drawable.profile_90)
        dataList.add(R.drawable.profile_91)
        dataList.add(R.drawable.profile_92)
        dataList.add(R.drawable.profile_93)
        dataList.add(R.drawable.profile_94)
        dataList.add(R.drawable.profile_95)
        dataList.add(R.drawable.profile_96)
        dataList.add(R.drawable.profile_97)
        dataList.add(R.drawable.profile_98)
        dataList.add(R.drawable.profile_99)
        dataList.add(R.drawable.profile_100)
        dataList.add(R.drawable.profile_101)
        dataList.add(R.drawable.profile_102)
        dataList.add(R.drawable.profile_103)
        dataList.add(R.drawable.profile_104)
        dataList.add(R.drawable.profile_106)
        dataList.add(R.drawable.profile_107)
        dataList.add(R.drawable.profile_108)
        dataList.add(R.drawable.profile_109)
        dataList.add(R.drawable.profile_110)
        dataList.add(R.drawable.profile_111)
        dataList.add(R.drawable.profile_112)
        dataList.add(R.drawable.profile_113)
        dataList.add(R.drawable.profile_114)
        dataList.add(R.drawable.profile_115)
        dataList.add(R.drawable.profile_116)
        dataList.add(R.drawable.profile_117)
        dataList.add(R.drawable.profile_118)
        dataList.add(R.drawable.profile_119)
        dataList.add(R.drawable.profile_120)
        dataList.add(R.drawable.profile_121)
        dataList.add(R.drawable.profile_122)
        dataList.add(R.drawable.profile_123)
        dataList.add(R.drawable.profile_124)
        dataList.add(R.drawable.profile_125)

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