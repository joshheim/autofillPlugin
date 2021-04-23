package com.github.joshheim.autofillplugin

import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep as BaseListPopupStep1

class AutofillPopupList(title: String, variables: Array<String>) : BaseListPopupStep1<String>(title,
    variables.toMutableList()
) {

    override fun isSpeedSearchEnabled(): Boolean {
        return true
    }

    override fun onChosen(selectedValue: String?, finalChoice: Boolean): PopupStep<*> {
        val setter = GetSettersActionClass()
        return FINAL_CHOICE
    }

}