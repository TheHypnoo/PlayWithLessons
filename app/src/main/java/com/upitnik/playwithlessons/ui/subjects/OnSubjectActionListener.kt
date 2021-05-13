package com.upitnik.playwithlessons.ui.subjects

import com.upitnik.playwithlessons.data.model.subject.SubjectsItem

interface OnSubjectActionListener {
    fun onSubjectClicked(Subject: SubjectsItem)
}