package com.nsz.kotlin.open.source

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nsz.kotlin.R
import com.nsz.kotlin.databinding.ItemWxAuthorLvBinding
import com.nsz.kotlin.databinding.ItemWxCommentLvBinding


class WXDetailActivity : AppCompatActivity() {

    private val authorList = ArrayList<Int>()
    private val authorDataList = ArrayList<Int>()
    private val commentDataList = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source_wx_detail)
        initData()
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        authorDataList.add(R.drawable.ic_author)
        authorDataList.add(R.drawable.profile_46)
        val set = HashSet<Int>()
        for (index in 1..48) {
            val random = (0..112).random()
            val item = authorList[random]
            set.add(item)
            if (index == 16) set.add(R.drawable.profile_126)
            if (index == 17) set.add(R.drawable.profile_39)
        }
        authorDataList.addAll(set)

        val gridLayoutManager = GridLayoutManager(this, 9)
        val authorRecyclerView = findViewById<RecyclerView>(R.id.author_recycler_view)
        authorRecyclerView.layoutManager = gridLayoutManager
        authorRecyclerView.adapter = AuthorAdapter()

        findViewById<TextView>(R.id.time_text).text = "2024年3月31号 10:34"
        findViewById<TextView>(R.id.title_text).text = "合肥蜀山向日葵幼儿园#传统美食文化#茶文化   本期摇篮分享红楼梦美食——《茶泡饭》#传统文化 #美食 #健康饮食\n" +
                "        曹雪芹写作《红楼梦》的前后，正是中国传统饮食文化的鼎盛时期。随着社会经济、文化的发展，中国的饮食业在清代的“康乾盛世”出现了极大的繁荣。《红楼梦》中对贾府的饮食作了细致传神的描写，据统计，《红楼梦》小说出现的美食多达180多种。                      《红楼梦》中提到的南方食俗还有六安茶、老君眉、龙井茶、普洱茶等南方茶饮。  茶艺是生活，是礼仪，更是文化，延承中华民族传统礼仪。"
        findViewById<ImageView>(R.id.task_image).setImageResource(R.drawable.task_3)

        var comment = Comment(R.drawable.ic_author, "zhou", "为完成学校任务，求各位大佬们继续点赞", "2024年3月31号 10:32")
        // commentDataList.add(comment)
        comment = Comment(R.drawable.profile_126, "徐荣 - 商米", "哈哈，没想到", "2024年3月31号 14:10")
        // commentDataList.add(comment)
        comment = Comment(R.drawable.profile_47, "王聪 - 商米", "可以的，", "2024年3月31号 14:10")
        // commentDataList.add(comment)
        comment = Comment(R.drawable.profile_39, "陈实", "老倪，你变了", "2024年3月31号 13:48")
        commentDataList.add(comment)

        val linearLayoutManager = LinearLayoutManager(this)
        val commentRecyclerView = findViewById<RecyclerView>(R.id.comment_recycler_view)
        commentRecyclerView.layoutManager = linearLayoutManager
        commentRecyclerView.adapter = CommentAdapter()
    }

    private fun initData() {
        authorList.add(R.drawable.profile_1)
        authorList.add(R.drawable.profile_2)
        authorList.add(R.drawable.profile_22)
        authorList.add(R.drawable.profile_3)
        authorList.add(R.drawable.profile_23)
        authorList.add(R.drawable.profile_4)
        authorList.add(R.drawable.profile_24)
        authorList.add(R.drawable.profile_5)
        authorList.add(R.drawable.profile_25)
        authorList.add(R.drawable.profile_21)
        authorList.add(R.drawable.profile_6)
        authorList.add(R.drawable.profile_36)
        authorList.add(R.drawable.profile_7)
        authorList.add(R.drawable.profile_37)
        authorList.add(R.drawable.profile_8)
        authorList.add(R.drawable.profile_38)
        authorList.add(R.drawable.profile_9)
        authorList.add(R.drawable.profile_39)
        authorList.add(R.drawable.profile_10)
        authorList.add(R.drawable.profile_40)
        authorList.add(R.drawable.profile_11)
        authorList.add(R.drawable.profile_41)
        authorList.add(R.drawable.profile_12)
        authorList.add(R.drawable.profile_42)
        authorList.add(R.drawable.profile_13)
        authorList.add(R.drawable.profile_43)
        authorList.add(R.drawable.profile_14)
        authorList.add(R.drawable.profile_44)
        authorList.add(R.drawable.profile_15)
        authorList.add(R.drawable.profile_45)
        authorList.add(R.drawable.profile_16)
        authorList.add(R.drawable.profile_17)
        authorList.add(R.drawable.profile_47)
        authorList.add(R.drawable.profile_18)
        authorList.add(R.drawable.profile_48)
        authorList.add(R.drawable.profile_19)
        authorList.add(R.drawable.profile_49)
        authorList.add(R.drawable.profile_20)
        authorList.add(R.drawable.profile_30)
        authorList.add(R.drawable.profile_31)
        authorList.add(R.drawable.profile_32)
        authorList.add(R.drawable.profile_33)
        authorList.add(R.drawable.profile_34)
        authorList.add(R.drawable.profile_26)
        authorList.add(R.drawable.profile_27)
        authorList.add(R.drawable.profile_28)
        authorList.add(R.drawable.profile_29)
        authorList.add(R.drawable.profile_35)
        authorList.add(R.drawable.profile_60)
        authorList.add(R.drawable.profile_61)
        authorList.add(R.drawable.profile_62)
        authorList.add(R.drawable.profile_63)
        authorList.add(R.drawable.profile_64)
        authorList.add(R.drawable.profile_65)
        authorList.add(R.drawable.profile_66)
        authorList.add(R.drawable.profile_67)
        authorList.add(R.drawable.profile_68)
        authorList.add(R.drawable.profile_69)
        authorList.add(R.drawable.profile_70)
        authorList.add(R.drawable.profile_71)
        authorList.add(R.drawable.profile_72)
        authorList.add(R.drawable.profile_73)
        authorList.add(R.drawable.profile_74)
        authorList.add(R.drawable.profile_75)
        authorList.add(R.drawable.profile_76)
        authorList.add(R.drawable.profile_77)
        authorList.add(R.drawable.profile_78)
        authorList.add(R.drawable.profile_79)
        authorList.add(R.drawable.profile_80)
        authorList.add(R.drawable.profile_81)
        authorList.add(R.drawable.profile_82)
        authorList.add(R.drawable.profile_83)
        authorList.add(R.drawable.profile_84)
        authorList.add(R.drawable.profile_85)
        authorList.add(R.drawable.profile_86)
        authorList.add(R.drawable.profile_87)
        authorList.add(R.drawable.profile_88)
        authorList.add(R.drawable.profile_89)
        authorList.add(R.drawable.profile_90)
        authorList.add(R.drawable.profile_91)
        authorList.add(R.drawable.profile_92)
        authorList.add(R.drawable.profile_93)
        authorList.add(R.drawable.profile_94)
        authorList.add(R.drawable.profile_95)
        authorList.add(R.drawable.profile_96)
        authorList.add(R.drawable.profile_97)
        authorList.add(R.drawable.profile_98)
        authorList.add(R.drawable.profile_99)
        authorList.add(R.drawable.profile_100)
        authorList.add(R.drawable.profile_101)
        authorList.add(R.drawable.profile_102)
        authorList.add(R.drawable.profile_103)
        authorList.add(R.drawable.profile_104)
        authorList.add(R.drawable.profile_106)
        authorList.add(R.drawable.profile_107)
        authorList.add(R.drawable.profile_108)
        authorList.add(R.drawable.profile_109)
        authorList.add(R.drawable.profile_110)
        authorList.add(R.drawable.profile_111)
        authorList.add(R.drawable.profile_112)
        authorList.add(R.drawable.profile_113)
        authorList.add(R.drawable.profile_114)
        authorList.add(R.drawable.profile_115)
        authorList.add(R.drawable.profile_116)
        authorList.add(R.drawable.profile_117)
        authorList.add(R.drawable.profile_118)
        authorList.add(R.drawable.profile_119)
        authorList.add(R.drawable.profile_120)
        authorList.add(R.drawable.profile_121)
        authorList.add(R.drawable.profile_122)
        authorList.add(R.drawable.profile_123)
        authorList.add(R.drawable.profile_124)
        authorList.add(R.drawable.profile_125)
        authorList.add(R.drawable.profile_126)
    }

    inner class AuthorAdapter : RecyclerView.Adapter<WXDetailActivity.AuthorViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWxAuthorLvBinding.inflate(layoutInflater)
            return AuthorViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return authorDataList.size
        }

        override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
            val item = authorDataList[position]
            holder.bind(item, position)
        }

    }

    inner class AuthorViewHolder(private var binding: ItemWxAuthorLvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resId: Int, position: Int) {
            binding.authorImage.setImageResource(resId)
        }

    }

    data class Comment(var resId: Int, val author: String, val content: String, val time: String)

    inner class CommentAdapter : RecyclerView.Adapter<WXDetailActivity.CommentViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWxCommentLvBinding.inflate(layoutInflater)
            return CommentViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return commentDataList.size
        }

        override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
            val item = commentDataList[position]
            holder.bind(item, position)
        }

    }

    inner class CommentViewHolder(private var binding: ItemWxCommentLvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment, position: Int) {
            binding.authorImage.setImageResource(item.resId)
            binding.authorText.text = item.author
            binding.commentText.text = item.content
            binding.timeText.text = item.time
        }

    }

}
