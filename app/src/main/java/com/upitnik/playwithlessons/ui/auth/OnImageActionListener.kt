package com.upitnik.playwithlessons.ui.auth

import com.upitnik.playwithlessons.data.model.auth.ImagesRegisterItem

interface OnImageActionListener {
    fun onImageClick(imageUser: ImagesRegisterItem)
}