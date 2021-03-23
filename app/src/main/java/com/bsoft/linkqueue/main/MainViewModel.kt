package com.bsoft.linkqueue.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsoft.linkqueue.room.LinkItem
import com.bsoft.linkqueue.room.MyRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    var emptyListTextVisibility : MutableLiveData<Int> = MutableLiveData()

    private var _addNewLink : MutableLiveData<Boolean> = MutableLiveData()
    var addNewLink : LiveData<Boolean> = _addNewLink

    private var _showAddLinkDialog : MutableLiveData<Boolean> = MutableLiveData()
    var showAddLinkDialog : LiveData<Boolean> = _showAddLinkDialog

    private var _itemList : MutableLiveData<List<LinkItem>> = MutableLiveData()
    var itemList : LiveData<List<LinkItem>> = _itemList

    private var _totalItem : MutableLiveData<String> = MutableLiveData()
    var totalItem : LiveData<String> = _totalItem




    init {
        emptyListTextVisibility.value = View.VISIBLE
        _totalItem.value = "0 link available"
    }

    fun addLinkDialog(){
        _showAddLinkDialog.value = true
    }

    fun addLink(){
        _addNewLink.value = true
    }

    fun saveLink(myRoomDatabase: MyRoomDatabase,linkItem: LinkItem){
        CoroutineScope(Dispatchers.IO).launch{
            myRoomDatabase.linkDao().insertLink(linkItem)
            withContext(Dispatchers.Main){
                _showAddLinkDialog.value = false
                getItemList(myRoomDatabase)
            }
        }
    }

    fun getItemList(myRoomDatabase: MyRoomDatabase){
        CoroutineScope(Dispatchers.IO).launch{
            val itemList = myRoomDatabase.linkDao().getAllLinks()
            withContext(Dispatchers.Main){
                _itemList.value = itemList
                if(itemList.isNullOrEmpty()){
                    emptyListTextVisibility.value = View.VISIBLE
                    _totalItem.value = "0 link available"
                }
                else{
                    emptyListTextVisibility.value = View.GONE
                    if(itemList.size>1) {
                        _totalItem.value = itemList.size.toString() + " links available"
                    }
                    else{
                        _totalItem.value = itemList.size.toString() + " link available"
                    }
                }
            }
        }
    }

    fun deleteItemList(myRoomDatabase: MyRoomDatabase,linkItem: LinkItem){
        CoroutineScope(Dispatchers.IO).launch{
            myRoomDatabase.linkDao().deleteLink(linkItem)
            withContext(Dispatchers.Main){
                getItemList(myRoomDatabase)
            }
        }
    }
}