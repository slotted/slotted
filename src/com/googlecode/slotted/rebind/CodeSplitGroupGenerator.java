package com.googlecode.slotted.rebind;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.googlecode.slotted.client.CodeSplitActivity;
import com.googlecode.slotted.client.CodeSplitPlace;
import com.googlecode.slotted.client.SlottedException;
import com.googlecode.slotted.client.SlottedPlace;

import java.io.PrintWriter;

public class CodeSplitGroupGenerator extends Generator {
    private static String NamePostfix = "Impl";
    private String typeName;

    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException
    {
        this.typeName = typeName;
        TypeOracle typeOracle = context.getTypeOracle();
        JClassType clazz = typeOracle.findType(typeName);

        if (clazz == null) {
            throw new UnableToCompleteException();
        }

        try {

            SourceWriter sourceWriter = getSourceWriter(clazz, context, logger);
            if (sourceWriter != null) {
                writeInitMethod(logger, context, typeOracle, sourceWriter);

                sourceWriter.commit(logger);
                logger.log(TreeLogger.DEBUG, "Done Generating source for "
                        + clazz.getName(), null);
            }

        } catch (NotFoundException e) {
            logger.log(TreeLogger.ERROR, "Error Generating source for " + typeName, e);
            throw new UnableToCompleteException();
        }

        return clazz.getQualifiedSourceName() + NamePostfix;
    }

    public SourceWriter getSourceWriter(JClassType classType, GeneratorContext context, TreeLogger logger) {

        String packageName = classType.getPackage().getName();
        String simpleName = classType.getSimpleSourceName() + NamePostfix;
        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);

        String implementName = classType.getQualifiedBinaryName();
        composer.addImplementedInterface(implementName);
        composer.addImport(GWT.class.getCanonicalName());
        composer.addImport(RunAsyncCallback.class.getCanonicalName());
        composer.addImport(Callback.class.getCanonicalName());
        composer.addImport(Activity.class.getCanonicalName());
        composer.addImport(SlottedPlace.class.getCanonicalName());
        composer.addImport(SlottedException.class.getCanonicalName());

        PrintWriter printWriter = context.tryCreate(logger, packageName,simpleName);

        if (printWriter == null) {
            return null;
        } else {
            SourceWriter sw = composer.createSourceWriter(context, printWriter);
            return sw;
        }

    }

    private void writeInitMethod(TreeLogger logger, GeneratorContext context, TypeOracle typeOracle, SourceWriter sourceWriter) throws NotFoundException, UnableToCompleteException {
        sourceWriter.println("@Override public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {");
        sourceWriter.indent();
        sourceWriter.println("GWT.runAsync(new RunAsyncCallback() {");
        sourceWriter.indent();
        sourceWriter.println("@Override public void onFailure(Throwable reason) {");
        sourceWriter.indent();
        sourceWriter.println("callback.onFailure(reason);");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
        sourceWriter.println("@Override public void onSuccess() {");
        sourceWriter.indent();

        JClassType[] types = typeOracle.getTypes();
        JClassType placeType = typeOracle.getType(CodeSplitPlace.class.getName());
        boolean first = true;
        for (JClassType place: types) {
            if (!place.isAbstract() && place.isDefaultInstantiable() && place.isAssignableTo(placeType)) {
                generateif(logger, place, first, sourceWriter);
                if (first) {
                    first = false;
                }
            }
        }
        if (first) {
            logger.log(TreeLogger.ERROR, "No matching CodeSplitPlaces for Group:" + typeName);
            throw new UnableToCompleteException();
        }

        sourceWriter.println("} else {");
        sourceWriter.indent();
        sourceWriter.println("callback.onFailure(new SlottedException(place.getClass().getName() + \" is not found.\"));");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("});");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    private void generateif(TreeLogger logger, JClassType placeType, boolean first, SourceWriter sourceWriter) throws NotFoundException, UnableToCompleteException {

        JMethod method = placeType.getMethod("getCodeSplitGroup", new JType[0]);
        CodeSplitActivity annotation = method.getAnnotation(CodeSplitActivity.class);
        if (annotation == null || annotation.value() == null || annotation.value().length != 1) {
            logger.log(TreeLogger.ERROR, "@CodeSplitActivity not defined on getCodeSplitGroup() for:" + placeType);
            throw new UnableToCompleteException();
        }
        Class<? extends Activity> activityClass = annotation.value()[0];

        if (!first) {
            sourceWriter.print("} else ");
        }
        sourceWriter.println("if (place instanceof " + placeType.getQualifiedSourceName() + ") {");
        sourceWriter.indent();
        sourceWriter.println("callback.onSuccess((Activity)GWT.create(" + activityClass.getCanonicalName() + ".class));");
        sourceWriter.outdent();
    }

}
