package com.bsoft.linkqueue.main

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsoft.linkqueue.R
import com.bsoft.linkqueue.databinding.ActivityMainBinding
import com.bsoft.linkqueue.databinding.LayoutDialogAddLinkBinding
import com.bsoft.linkqueue.room.DatabaseBuilder
import com.bsoft.linkqueue.room.LinkItem
import com.bsoft.linkqueue.room.MyRoomDatabase
import com.bsoft.linkqueue.utils.Extensions.Companion.toast

class MainActivity : AppCompatActivity() {

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mAddLinkDialogBinding : LayoutDialogAddLinkBinding
    private lateinit var mMyRoomDatabase : MyRoomDatabase
    private lateinit var mListItemAdapter: ListItemAdapter
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.mainViewModel = mMainViewModel
        mBinding.lifecycleOwner = this

        initData()
        initObserver()
    }

    private fun initData(){
       mMyRoomDatabase =  DatabaseBuilder(this).getInstance()
        mListItemAdapter = ListItemAdapter(arrayListOf(),object : ClickListener{
            override fun onClick(linkItem: LinkItem) {
                val packageManager = this@MainActivity.packageManager
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkItem.link))
                if (intent.resolveActivity(packageManager)!=null) {
                    startActivity(intent)
                }
                else{
                    this@MainActivity.toast("Link not Valid")
                }
            }

            override fun onDelete(linkItem: LinkItem) {
                mMainViewModel.deleteItemList(mMyRoomDatabase,linkItem)
            }

        })
        mBinding.listItemRecyclerView.adapter = mListItemAdapter
        mBinding.listItemRecyclerView.layoutManager = LinearLayoutManager(this)
        mMainViewModel.getItemList(mMyRoomDatabase)
    }

    private fun initObserver(){
        mMainViewModel.showAddLinkDialog.observe(this, Observer {
            if (it!=null && it){
                showAddLinkDialog()
            }
            else{
                dialog.dismiss()
            }
        })

        mMainViewModel.addNewLink.observe(this, Observer {
           mAddLinkDialogBinding.apply {
               if (title.text.toString().trim().isNotEmpty() && link.text.toString().trim().isNotEmpty()) {
                   val linkItem =
                       LinkItem(title = title.text.toString(), link = link.text.toString())
                   mMainViewModel.saveLink(mMyRoomDatabase, linkItem)
               }else{
                   this@MainActivity.toast("Enter valid title and link")
               }
           }
        })

        mMainViewModel.itemList.observe(this, Observer {
            if (it!=null) {
                Log.d("TEST","List ITem ${it.size}")
                mListItemAdapter.updateList(it)
            }
        })
    }

    private fun showAddLinkDialog(){
        dialog = Dialog(this)
        mAddLinkDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.layout_dialog_add_link,null,false)
        mAddLinkDialogBinding.mainViewModel = mMainViewModel
        mAddLinkDialogBinding.lifecycleOwner = this
        dialog.setContentView(mAddLinkDialogBinding.root)
        dialog.show()
    }
}