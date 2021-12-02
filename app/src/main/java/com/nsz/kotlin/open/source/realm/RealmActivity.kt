package com.nsz.kotlin.open.source.realm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityOpenSourceRealmBinding
import io.realm.Realm

class RealmActivity : AppCompatActivity() {

    private val binding: ActivityOpenSourceRealmBinding by lazy { ActivityOpenSourceRealmBinding.inflate(layoutInflater) }
    private val realm by lazy { Realm.getDefaultInstance() }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private val dog = Dog()
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

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}