package com.github.joshheim.autofillplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl


class FillAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        val document: Document = editor!!.document
        val phpClass: PhpClassImpl = document as PhpClassImpl

        val autofillPopupList = AutofillPopupList("Fill", mutableListOf(phpClass.attributes.toString()).toTypedArray())

        if (e.project != null){
            JBPopupFactory.getInstance().createListPopup(autofillPopupList, 5).showCenteredInCurrentWindow(e.project!!)
        }




    }



}

