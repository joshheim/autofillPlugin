package com.github.joshheim.autofillplugin.services

import com.github.joshheim.autofillplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
