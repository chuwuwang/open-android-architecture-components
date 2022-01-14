package com.nsz.kotlin.open.source

import android.os.Bundle
import android.view.View
import com.nsz.kotlin.aac.ViewBindingFragment
import com.nsz.kotlin.databinding.FragmentOpenSourceRealmBinding
import io.realm.Realm

class RealmFragment : ViewBindingFragment<FragmentOpenSourceRealmBinding>() {

    private val realm by lazy { Realm.getDefaultInstance() }

    private val dog = Dog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle ? ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.mbOk.setOnClickListener {
            val string = binding.editInput.text.toString()
            dog.name = string
            realm.executeTransaction {
                it.copyToRealmOrUpdate(dog)
            }
            realm.executeTransaction {
                val results = it.where(Dog::class.java).findAll()
                val stringBuilder = StringBuilder()
                for (item in results) {
                    stringBuilder.append(item.name)
                    stringBuilder.append(" -> ")
                }
                binding.tvData.text = stringBuilder.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}