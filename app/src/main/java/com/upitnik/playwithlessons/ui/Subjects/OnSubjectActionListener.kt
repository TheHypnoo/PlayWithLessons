package com.upitnik.playwithlessons.ui.Subjects

import com.upitnik.playwithlessons.data.model.subject.SubjectsItem

interface OnSubjectActionListener {
    fun onSubjectClicked(Subject: SubjectsItem)
}