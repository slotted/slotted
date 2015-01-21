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
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.googlecode.slotted.client.GenerateGinSingletons;


@SupportedAnnotationTypes({"com.googlecode.slotted.client.GenerateGinSingletons", "javax.inject.Singleton", "com.google.inject.Singleton"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class GinSingletonProcessor extends AbstractProcessor {
    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        LinkedList<GenStruct> genStructs = new LinkedList<GenStruct>();
        for (Element element: roundEnv.getElementsAnnotatedWith(GenerateGinSingletons.class)) {
            GenerateGinSingletons genAnnotation = element.getAnnotation(GenerateGinSingletons.class);
            GenStruct genStruct = new GenStruct();
            genStruct.fullPackage = genAnnotation.fullPackage();
            genStruct.baseName = genAnnotation.baseName();
            genStruct.scanPackages = genAnnotation.scanPackages();
            genStructs.add(genStruct);
        }

        if (!genStructs.isEmpty()) {
            for (TypeElement annotation : annotations) {
                if (!"com.googlecode.slotted.client.GenerateGinSingletons".equals(annotation.getQualifiedName().toString())) {
                    for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                        if (element.getKind().isClass() || element.getKind().isInterface()) {
                            TypeElement typeElement = (TypeElement) element;
                            String className = typeElement.getQualifiedName().toString();
                            for (GenStruct genStruct : genStructs) {
                                for (String packageStart : genStruct.scanPackages) {
                                    if (className.startsWith(packageStart)) {
                                        genStruct.singletonTypes.add(typeElement);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (GenStruct genStruct: genStructs) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating Ginjector and Module for:" + genStruct.baseName);
                writeSingletonGinjector(genStruct);
                writeSingletonModule(genStruct);
            }
        }

        return false;
    }

    private void writeSingletonModule(GenStruct genStruct)  {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(genStruct.fullPackage + "." + genStruct.baseName + "Module");

            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package ").append(genStruct.fullPackage).append(";\n");
            bw.newLine();
            bw.append("import javax.inject.Singleton;\n");
            bw.append("import com.google.gwt.inject.client.AbstractGinModule;\n");
            bw.append("import com.google.inject.Provides;\n");
            bw.newLine();
            bw.append("public class SingletonModule extends AbstractGinModule {\n");
            bw.append("\t@Override\n");
            bw.append("\tprotected void configure() {\n");
            bw.append("\t}\n");
            bw.newLine();
            for (TypeElement type: genStruct.singletonTypes) {
                bw.append("\t@Provides @Singleton\n");
                bw.append("\tpublic ").append(type.getQualifiedName()).append(" get").append(type.getSimpleName()).append("() {\n");
                bw.append("\t\treturn SingletonGinjector.instance.get").append(type.getSimpleName()).append("();\n");
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
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(genStruct.fullPackage + "." + genStruct.baseName + "Ginjector");

            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package ").append(genStruct.fullPackage).append(";\n");
            bw.newLine();
            bw.append("import com.google.gwt.core.client.GWT;\n");
            bw.append("import com.google.gwt.inject.client.GinModules;\n");
            bw.append("import com.google.gwt.inject.client.Ginjector;\n");
            bw.newLine();
            bw.append("@GinModules({})\n");
            bw.append("public interface SingletonGinjector extends Ginjector {\n");
            bw.newLine();
            bw.append("\tpublic static final SingletonGinjector instance = GWT.create(SingletonGinjector.class);\n");
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
        public String fullPackage;
        public String baseName;
        public String[] scanPackages;
        public LinkedList<TypeElement> singletonTypes = new LinkedList<TypeElement>();
    }
}
