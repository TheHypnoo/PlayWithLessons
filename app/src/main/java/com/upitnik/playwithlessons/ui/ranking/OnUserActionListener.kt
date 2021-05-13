package com.upitnik.playwithlessons.ui.ranking

import com.upitnik.playwithlessons.data.model.auth.UserItem

interface OnUserActionListener {
    fun onUserClick(user: UserItem)
}