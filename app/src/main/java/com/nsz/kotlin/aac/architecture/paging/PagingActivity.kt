package com.nsz.kotlin.aac.architecture.paging

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nsz.kotlin.databinding.ActivityAacArchitecturePagingBinding
import com.nsz.kotlin.ux.common.CommonLog

class PagingActivity : AppCompatActivity() {

    private val binding: ActivityAacArchitecturePagingBinding by lazy { ActivityAacArchitecturePagingBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<CheeseViewModel>()

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val cheeseAdapter = CheeseAdapter()
        binding.recyclerView.adapter = cheeseAdapter
        viewModel.allCheeseList.observe(this) {
            cheeseAdapter.submitList(it)
        }
        binding.btnAdd.setOnClickListener {
            val inputText = binding.editInput.text.toString()
            val notBlank = inputText.isNotBlank()
            if (notBlank) {
                viewModel.insert(inputText)
                binding.editInput.setText("")
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            CommonLog.e("getMovementFlags")
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            CommonLog.e("onMove")
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            CommonLog.e("onSwiped")
            (viewHolder as CheeseAdapter.CheeseViewHolder).cheese?.let {
                viewModel.remove(it)
            }
        }

    }

}