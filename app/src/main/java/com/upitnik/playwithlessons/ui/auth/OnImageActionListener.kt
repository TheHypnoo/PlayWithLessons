package com.upitnik.playwithlessons.ui.auth

import com.upitnik.playwithlessons.data.model.auth.imageUser

interface OnImageActionListener {
    fun onImageClick(imageUser: imageUser)
}