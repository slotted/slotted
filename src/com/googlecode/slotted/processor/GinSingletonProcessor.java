package com.googlecode.slotted.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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


@SupportedAnnotationTypes({"javax.inject.Singleton", "com.google.inject.Singleton"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class GinSingletonProcessor extends AbstractProcessor {
    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        List<TypeElement> singletonTypes = new LinkedList<TypeElement>();
        for (TypeElement annotation: annotations) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "SingletonProcessing:" + annotation.getQualifiedName());
            for (Element element: roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element.getKind().isClass() || element.getKind().isInterface()) {
                    singletonTypes.add((TypeElement) element);
                }
            }
        }

        writeSingletonGinjector(singletonTypes);
        writeSingletonModule(singletonTypes);

        return false;
    }

    private void writeSingletonModule(List<TypeElement> singletonTypes)  {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile("com.googlecode.slotted.client.SingletonModule");

            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package com.googlecode.slotted.client;\n");
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
            for (TypeElement type: singletonTypes) {
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

    private void writeSingletonGinjector(List<TypeElement> singletonTypes)  {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile("com.googlecode.slotted.client.SingletonGinjector");

            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package com.googlecode.slotted.client;\n");
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
            for (TypeElement type: singletonTypes) {
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

}
