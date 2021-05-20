package com.upitnik.playwithlessons.ui.ranking

import com.upitnik.playwithlessons.data.model.authentication.User

interface OnUserActionListener {
    fun onUserClick(user: User)
}