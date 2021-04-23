package com.github.joshheim.autofillplugin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.util.SmartList
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl

class GenerateService {
    fun generateMethods(event: AnActionEvent, typeOfMethod: String?) {
        val project: Project? = event.getData(PlatformDataKeys.PROJECT)
        val editor: Editor? = event.getData(PlatformDataKeys.EDITOR)
        if (editor != null) {
            val caret: CaretModel = editor.caretModel
            val file = event.getData(LangDataKeys.PSI_FILE)
            if (file != null) {
                val selectedElement = file.findElementAt(caret.offset)
                if (selectedElement != null && selectedElement.parent.reference != null) {
                    val variableDeclaration = selectedElement.parent.reference!!.resolve()
                    if (variableDeclaration != null) {
                        val assignmentExpression = variableDeclaration.context
                        if (assignmentExpression != null) {
                            val selectedElementClass: MutableCollection<PsiElement?> = SmartList()
                            assignmentExpression.accept(object : PsiRecursiveElementVisitor() {
                                override fun visitElement(psiElement: PsiElement) {
                                    if (psiElement is ClassReferenceImpl && psiElement.reference != null) {
                                        selectedElementClass.add(psiElement.reference!!.resolve())
                                    }
                                    super.visitElement(psiElement)
                                }
                            })

                            val document: Document = editor.document
                            var firstMethodGeneration = true
                            for (initClass in selectedElementClass) {
                                val phpClass: PhpClassImpl = initClass as PhpClassImpl
                                for (method in phpClass.methods) {
                                    val methodName: String = method.name

                                    // If method name has "set" on position 0 add it to editor
                                    if (methodName.indexOf(typeOfMethod!!) == 0) {
                                        val firstSetter = firstMethodGeneration
                                        WriteCommandAction.runWriteCommandAction(project) {
                                            if (firstSetter) {
                                                caret.moveToOffset(caret.visualLineEnd)
                                                document.insertString(caret.offset, "\n")
                                            }

                                            caret.moveToOffset(caret.visualLineEnd)
                                            document.insertString(
                                                caret.offset, selectedElement.text
                                                    .toString() + "->" + methodName + "();\n"
                                            )
                                        }
                                    }
                                    firstMethodGeneration = false
                                }
                            }
                        }
                    }
                }
            }
        }



    }
}