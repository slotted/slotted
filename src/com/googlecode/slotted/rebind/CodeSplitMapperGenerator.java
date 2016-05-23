package com.googlecode.slotted.rebind;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.googlecode.slotted.client.CodeSplit;
import com.googlecode.slotted.client.CodeSplitLoadException;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.SlottedException;
import com.googlecode.slotted.client.SlottedPlace;

public class CodeSplitMapperGenerator extends Generator {
    private static String NamePostfix = "Impl";

    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException
    {
        TypeOracle typeOracle = context.getTypeOracle();
        JClassType clazz = typeOracle.findType(typeName);

        if (clazz == null) {
            throw new UnableToCompleteException();
        }

        try {

            SourceWriter sourceWriter = getSourceWriter(logger, context, clazz);
            if (sourceWriter != null) {
                List<JClassType> codeSplitPlaces = getCodeSplitPlaces(logger, typeOracle, typeName);
                writeGetMethod(logger, sourceWriter);
                writeGetActivityMethod(logger, sourceWriter, codeSplitPlaces);

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

    protected SourceWriter getSourceWriter(TreeLogger logger, GeneratorContext context, JClassType classType) {

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
        composer.addImport(CodeSplitLoadException.class.getCanonicalName());

        PrintWriter printWriter = context.tryCreate(logger, packageName,simpleName);

        if (printWriter == null) {
            return null;
        } else {
            SourceWriter sw = composer.createSourceWriter(context, printWriter);
            return sw;
        }

    }

    protected List<JClassType> getCodeSplitPlaces(TreeLogger logger, TypeOracle typeOracle, String mapperType) throws NotFoundException, UnableToCompleteException {
        List<JClassType> codeSplitPlaces = new LinkedList<JClassType>();
        JClassType[] types = typeOracle.getTypes();
        JClassType placeType = typeOracle.getType(Place.class.getName());
        for (JClassType place: types) {
            if (place.isAssignableTo(placeType)) {
                Annotation annotation = place.getAnnotation(CodeSplit.class);
                if (annotation != null) {
                    Class codeSplitClass = ((CodeSplit) annotation).value();
                    if (codeSplitClass != null && mapperType.equals(codeSplitClass.getCanonicalName())) {
                        if (place.isAbstract() || !place.isDefaultInstantiable()) {
                            logger.log(Type.ERROR, "Place is abstract or not default instantiable:" + place.getName());
                            throw new UnableToCompleteException();
                        }

                        codeSplitPlaces.add(place);
                    }
                }
            }
        }
        if (codeSplitPlaces.isEmpty()) {
            logger.log(Type.ERROR, "No places found for CodeSplitMapper:" + mapperType);
            throw new UnableToCompleteException();
        }

        return codeSplitPlaces;
    }

    protected void writeGetMethod(TreeLogger logger, SourceWriter sourceWriter) throws NotFoundException, UnableToCompleteException {
        sourceWriter.println("private boolean loaded = false;");
        sourceWriter.println("public boolean isLoaded() {");
        sourceWriter.indent();
        sourceWriter.println("return loaded;");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println("public void load(Callback<? super Activity, ? super Throwable> callback) {");
        sourceWriter.indent();
        sourceWriter.println("get(null, callback);");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println("public void get(final SlottedPlace place, final Callback<? super Activity, ? super Throwable> callback) {");
        sourceWriter.indent();
        sourceWriter.println("GWT.runAsync(new RunAsyncCallback() {");
        sourceWriter.indent();
        sourceWriter.println("public void onFailure(Throwable reason) {");
        sourceWriter.indent();
	    sourceWriter.println("callback.onFailure(new CodeSplitLoadException(\"Code Splitting load failed\", reason));");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
        sourceWriter.println("public void onSuccess() {");
        sourceWriter.indent();
        sourceWriter.println("loaded = true;");
        sourceWriter.println("if (place == null) {");
        sourceWriter.indent();
        sourceWriter.println("callback.onSuccess(null);");
        sourceWriter.outdent();

        sourceWriter.println("} else {");
        sourceWriter.indent();
        sourceWriter.println("try {");
        sourceWriter.indent();
        sourceWriter.println("Activity activity = getActivity(place);");
        sourceWriter.println("if (activity != null) {");
        sourceWriter.indent();
        sourceWriter.println("callback.onSuccess(activity);");
        sourceWriter.outdent();
        sourceWriter.println("} else {");
        sourceWriter.indent();
        sourceWriter.println("callback.onFailure(new SlottedException(place.getClass().getName() + \" is not found.\"));");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("} catch (Exception e) {");
        sourceWriter.indent();
        sourceWriter.println("callback.onFailure(e);");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("});");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    protected void writeGetActivityMethod(TreeLogger logger, SourceWriter sourceWriter, List<JClassType> codeSplitPlaces) throws NotFoundException, UnableToCompleteException {
        sourceWriter.println("public void getActivity(final SlottedPlace place) {");
        sourceWriter.indent();
        for (JClassType place: codeSplitPlaces) {
            generateIf(logger, sourceWriter, place);
        }
        sourceWriter.println();
        sourceWriter.println("return null;");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    protected void generateIf(TreeLogger logger, SourceWriter sourceWriter, JClassType placeType) throws NotFoundException, UnableToCompleteException {
        PlaceActivity annotation = placeType.getAnnotation(PlaceActivity.class);
        if (annotation == null || annotation.value() == null) {
            logger.log(TreeLogger.ERROR, "@PlaceActivity not defined on:" + placeType);
            throw new UnableToCompleteException();
        }
        sourceWriter.println("if (place instanceof " + placeType.getQualifiedSourceName() + ") {");

        Class<? extends Activity>[] activityClasses = annotation.value();
        if (activityClasses.length == 1) {
            writeCreate(sourceWriter, activityClasses[0]);

        } else {
            sourceWriter.indent();
            sourceWriter.println("Class activityClass = place.getActivityClass();");
            for (Class activityClass: activityClasses) {
                sourceWriter.println("if (" + activityClass.getCanonicalName() + ".class.equals(activityClass)) {");
                writeCreate(sourceWriter, activityClass);
                sourceWriter.println("}");
            }
            sourceWriter.print("throw new IllegalStateException(\"Place needs to have getActivityClass() that returns one of these:\\n");
            for (Class activityClass: activityClasses) {
                sourceWriter.print(activityClass + "\\n");
            }
            sourceWriter.println("\");");
            sourceWriter.outdent();
        }

        sourceWriter.println("}");
    }

    private void writeCreate(SourceWriter sourceWriter, Class<? extends Activity> activityClass) throws UnableToCompleteException {
        sourceWriter.indent();
        sourceWriter.println("return (Activity)GWT.create(" + activityClass.getCanonicalName() + ".class);");
        sourceWriter.outdent();
    }


}
