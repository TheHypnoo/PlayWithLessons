package com.upitnik.playwithlessons.ui.Subjects

import com.upitnik.playwithlessons.data.model.subject.Subject

interface OnSubjectActionListener {
    fun onSubjectClicked(Subject: Subject)
}