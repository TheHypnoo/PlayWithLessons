package com.upitnik.playwithlessons.ui.auth

import com.upitnik.playwithlessons.data.model.authentication.Images

interface OnImageActionListener {
    fun onImageClick(imageUser: Images)
}