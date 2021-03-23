package com.bsoft.linkqueue.main

import com.bsoft.linkqueue.room.LinkItem

interface ClickListener {
    fun onClick(linkItem: LinkItem)
    fun onDelete(linkItem: LinkItem)
}