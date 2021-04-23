package com.github.joshheim.autofillplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.annotations.NotNull

class GetSettersActionClass : AnAction() {
    override fun actionPerformed(e: @NotNull AnActionEvent) {
        val generateService = GenerateService()
        generateService.generateMethods(e, "set")
    }

}