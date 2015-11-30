package com.googlecode.slotted.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.googlecode.slotted.client.GenerateGinSingletons;
import com.googlecode.slotted.client.GlobalSingleton;


@SupportedAnnotationTypes({"com.googlecode.slotted.client.GenerateGinSingletons", "com.googlecode.slotted.client.GlobalSingleton", "javax.inject.Singleton", "com.google.inject.Singleton"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class GinSingletonProcessor extends AbstractProcessor {
    private int runCount = 0;

    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        LinkedList<GenStruct> genStructs = new LinkedList<GenStruct>();
        for (Element element: roundEnv.getElementsAnnotatedWith(GenerateGinSingletons.class)) {
            GenerateGinSingletons genAnnotation = element.getAnnotation(GenerateGinSingletons.class);
            GenStruct genStruct = new GenStruct();
            genStruct.annotation = genAnnotation;
            genStructs.add(genStruct);
        }

        if (genStructs.isEmpty()) {
            if (runCount == 0) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "No GenerateGinSingletons found, skipping Singleton object creation");
            }

        } else {
            findGlobalSingletons(roundEnv, genStructs);
            findSingletons(annotations, roundEnv, genStructs);

            for (GenStruct genStruct: genStructs) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating Ginjector and Module for:" + genStruct.annotation.baseName());
                writeSingletonGinjector(genStruct);
                writeSingletonModule(genStruct);
            }
        }

        runCount++;
        return false;
    }

    private void findSingletons(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, LinkedList<GenStruct> genStructs) {
        for (TypeElement annotation : annotations) {
            if (!"GenerateGinSingletons".equals(annotation.getSimpleName().toString()) &&
                    !"GlobalSingleton".equals(annotation.getSimpleName().toString())) {

                for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                    GlobalSingleton globalAnnotation = element.getAnnotation(GlobalSingleton.class);
                    if ((element.getKind().isClass() || element.getKind().isInterface()) &&
                            (globalAnnotation == null || globalAnnotation.value())) {
                        TypeElement typeElement = (TypeElement) element;
                        String className = typeElement.getQualifiedName().toString();
                        for (GenStruct genStruct : genStructs) {
                            for (String packageStart : genStruct.annotation.scanPackages()) {
                                if (className.startsWith(packageStart)) {
                                    genStruct.singletonTypes.add(typeElement);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void findGlobalSingletons(RoundEnvironment roundEnv, LinkedList<GenStruct> genStructs) {
        for (Element element: roundEnv.getElementsAnnotatedWith(GlobalSingleton.class)) {
            GlobalSingleton globalAnnotation = element.getAnnotation(GlobalSingleton.class);
            if (globalAnnotation.value() && element.getKind() == ElementKind.METHOD) {
                Element parentElement = element.getEnclosingElement();
                while (parentElement != null && !parentElement.getKind().isClass()) {
                    parentElement = parentElement.getEnclosingElement();
                }
                TypeElement moduleType = (TypeElement) parentElement;
                if (moduleType != null) {
                    TypeElement singletonType = element.asType().accept(new SimpleTypeVisitor6<TypeElement, Void>() {
                        @Override public TypeElement visitExecutable(ExecutableType t, Void aVoid) {
                            DeclaredType dType = (DeclaredType) t.getReturnType();
                            return (TypeElement) dType.asElement();
                        }
                    }, null);

                    for (GenStruct genStruct : genStructs) {
                        for (String module: genStruct.annotation.modules()) {
                            if (module.equals(moduleType.getQualifiedName().toString())) {
                                genStruct.singletonTypes.add(singletonType);
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeSingletonModule(GenStruct genStruct)  {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(genStruct.annotation.fullPackage() + "." + genStruct.annotation.baseName() + "Module");

            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package ").append(genStruct.annotation.fullPackage()).append(";\n");
            bw.newLine();
            bw.append("import javax.inject.Singleton;\n");
            bw.append("import com.google.gwt.inject.client.AbstractGinModule;\n");
            bw.append("import com.google.inject.Provides;\n");
            bw.newLine();
            bw.append("public class ").append(genStruct.annotation.baseName()).append("Module extends AbstractGinModule {\n");
            bw.append("\t@Override\n");
            bw.append("\tprotected void configure() {\n");
            bw.append("\t}\n");
            bw.newLine();
            for (TypeElement type: genStruct.singletonTypes) {
                bw.append("\t@Provides\n");
                bw.append("\tpublic ").append(type.getQualifiedName()).append(" get").append(type.getSimpleName()).append("() {\n");
                bw.append("\t\treturn ").append(genStruct.annotation.baseName()).append("Ginjector.INSTANCE.get").append(type.getSimpleName()).append("();\n");
                bw.append("\t}\n");
                bw.newLine();
            }
            bw.append("}\n");

            bw.flush();
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSingletonGinjector(GenStruct genStruct)  {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(genStruct.annotation.fullPackage() + "." + genStruct.annotation.baseName() + "Ginjector");

            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package ").append(genStruct.annotation.fullPackage()).append(";\n");
            bw.newLine();
            bw.append("import com.google.gwt.core.client.GWT;\n");
            bw.append("import com.google.gwt.inject.client.GinModules;\n");
            bw.append("import com.google.gwt.inject.client.Ginjector;\n");
            bw.newLine();
            bw.append("@GinModules({");
            boolean first = true;
            for (String module: genStruct.annotation.modules()) {
                if (!first) {
                    bw.append(", ");
                }
                bw.append(module).append(".class");
                first = false;
            }
            bw.append("})\n");
            bw.append("public interface ").append(genStruct.annotation.baseName()).append("Ginjector extends Ginjector {\n");
            bw.newLine();
            bw.append("\tpublic static final ").append(genStruct.annotation.baseName()).append("Ginjector INSTANCE = GWT.create(")
                    .append(genStruct.annotation.baseName()).append("Ginjector.class);\n");
            bw.newLine();
            for (TypeElement type: genStruct.singletonTypes) {
                bw.append("\t").append(type.getQualifiedName()).append(" get").append(type.getSimpleName()).append("();\n");
                bw.newLine();
            }
            bw.append("}\n");

            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class GenStruct {
        public GenerateGinSingletons annotation;
        public LinkedList<TypeElement> singletonTypes = new LinkedList<TypeElement>();
    }

    private class GlobalStruct {
        public TypeElement ginjector;
        public TypeElement singletonType;
    }
}
