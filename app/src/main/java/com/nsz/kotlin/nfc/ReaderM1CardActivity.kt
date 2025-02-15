package com.nsz.kotlin.nfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.R
import com.nsz.kotlin.ux.common.ByteHelper
import com.nsz.kotlin.ux.common.CommonLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ReaderM1CardActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
    private lateinit var adapter: NfcAdapter

    private val intentFilter1 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
        try {
            addDataType("*/*")    /* Handles all MIME based dispatches. You should specify only the ones that you need. */
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
    }
    private val intentFilter2 = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
    private val intentFilter3 = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    private var filters = arrayOf(intentFilter1, intentFilter2, intentFilter3)
    private var techLists = arrayOf(
        arrayOf(NfcA::class.java.name),
        arrayOf(NfcB::class.java.name),
        arrayOf(NfcF::class.java.name),
        arrayOf(NfcV::class.java.name),
        arrayOf(Ndef::class.java.name),
        arrayOf(IsoDep::class.java.name),
        arrayOf(MifareClassic::class.java.name),
        arrayOf(NdefFormatable::class.java.name),
        arrayOf(MifareUltralight::class.java.name)
    )

    private lateinit var nfcManager: NfcManager

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_test)
        initView()
    }

    private fun initView() {
        val hasNFCFunction = NFCHelper.hasNFCFunction(this)
        if (hasNFCFunction) {
            val isEnable = NFCHelper.isNFCFunctionEnable(this)
            if (isEnable) {
                adapter = NfcAdapter.getDefaultAdapter(this)
            } else {
                // 请在系统设置中先启用NFC功能
                CommonLog.e("NFC功能未开启")
                finish()
            }
        } else {
            CommonLog.e("设备不支持NFC")
            finish()
        }
        nfcManager = NfcManager(this)

        findViewById<View>(R.id.mb_start_read_card).setOnClickListener {
            nfcManager.setReaderMode(true)
        }
        findViewById<View>(R.id.mb_stop_read_card).setOnClickListener {
            nfcManager.setReaderMode(false)
        }
        findViewById<View>(R.id.mb_card_pse).setOnClickListener {
            var selectPPSE = byteArrayOf(
                0x00,
                0xA4.toByte(),
                0x04,
                0x00,
                0x07,
                0xA0.toByte(),
                0x00,
                0x00,
                0x00,
                0x03,
                0x10,
                0x10,
                0x00,
            )
            selectPPSE = ByteHelper.hexString2Bytes("00A404000E325041592E5359532E444446303100")
            nfcManager.sendData(selectPPSE)
        }
    }

    override fun onResume() {
        super.onResume()
        CommonLog.e("onResume")
        // adapter.enableForegroundDispatch(this, pendingIntent, null, null)
        // adapter.enableForegroundDispatch(this, pendingIntent, filters, techLists)
        // detection()
        nfcManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        CommonLog.e("onPause")
        // adapter.disableForegroundDispatch(this)
        nfcManager.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        CommonLog.e("onNewIntent")
        // super.onNewIntent(intent)
        // setIntent(intent)
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action || NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            nfcManager.onTagDiscovered(tag)
        } else {
            super.onNewIntent(intent)
        }
    }

    private fun detection() {
        if (intent != null) {
            val action = intent.action
            CommonLog.e("action: $action")
            when (action) {
                NfcAdapter.ACTION_NDEF_DISCOVERED -> {
                    val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                    if (parcelables != null) {
                        CommonLog.e("-----------------------------------------------------------------")
                        parcelables.also { rawMessages ->
                            val messages: List<NdefMessage> = rawMessages.map {
                                CommonLog.e("item tag: $it")
                                it as NdefMessage
                            }
                            CommonLog.e("-----------------------------------------------------------------")
                            // Process the messages array.
                        }
                    } else {
                        CommonLog.e("parcelables is null")
                    }
                    processIntent()
                }
                NfcAdapter.ACTION_TECH_DISCOVERED -> processIntent()
                NfcAdapter.ACTION_TAG_DISCOVERED -> processIntent()
            }
        }
    }

    private fun processIntent() {
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            CommonLog.e("-----------------------------------------------------------------")
            val techList = tag.techList
            for (item in techList) {
                CommonLog.e("item tag: $item")
            }
            CommonLog.e("-----------------------------------------------------------------")
            processMiFareClassic(tag)
        }
    }

    private fun processMiFareClassic(tag: Tag) {
        val mifareClassic: MifareClassic = MifareClassic.get(tag)
        launch(Dispatchers.IO) {
            try {
                mifareClassic.connect()
                val type = mifareClassic.type                  // 获取TAG的类型
                val blockCount = mifareClassic.blockCount      // 获取TAG中包含的块数
                val sectorCount = mifareClassic.sectorCount    // 获取TAG中包含的扇区数
                CommonLog.e("MiFareClassic type: $type")
                CommonLog.e("MiFareClassic blockCount: $blockCount")
                CommonLog.e("MiFareClassic sectorCount: $sectorCount")
                CommonLog.e("-----------------------------------------------------------------")
                for (index in 0 until sectorCount) {
                    CommonLog.e("MiFareClassic 扇区$index 开始验证")
                    val authenticateA = mifareClassic.authenticateSectorWithKeyA(index, MifareClassic.KEY_DEFAULT)
                    if (authenticateA) {
                        CommonLog.e("MiFareClassic 扇区$index 验证成功")
                        val blockCountInSector = mifareClassic.getBlockCountInSector(index)
                        var sectorToBlock = mifareClassic.sectorToBlock(index)
                        CommonLog.e("MiFareClassic 扇区$index 中的块数: $blockCountInSector")
                        CommonLog.e("MiFareClassic 扇区$index 中的第一个块: $sectorToBlock")
                        for (pos in 0 until blockCountInSector) {
                            val readBlock = mifareClassic.readBlock(sectorToBlock)
                            val hexString = ByteHelper.bytes2HexString(readBlock)
                            CommonLog.e("MiFareClassic 扇区$index 中的第 $pos 个块中的数据: $hexString")
                            sectorToBlock++
                        }
                    } else {
                        CommonLog.e("MiFareClassic 扇区$index 验证失败")
                    }
                    CommonLog.e("-----------------------------------------------------------------")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mifareClassic.close()
            }
        }
    }

}