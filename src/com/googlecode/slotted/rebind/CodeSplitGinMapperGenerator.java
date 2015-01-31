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
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.googlecode.slotted.client.CodeSplit;
import com.googlecode.slotted.client.CodeSplitGinMapper;
import com.googlecode.slotted.client.PlaceActivity;
import com.googlecode.slotted.client.SlottedException;
import com.googlecode.slotted.client.SlottedPlace;

public class CodeSplitGinMapperGenerator extends Generator {
    private static String NamePostfix = "Impl";

    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException
    {
        TypeOracle typeOracle = context.getTypeOracle();
        JClassType classType = typeOracle.findType(typeName);
        JClassType ginMapperType = typeOracle.findType(CodeSplitGinMapper.class.getCanonicalName());

        if (classType == null) {
            throw new UnableToCompleteException();
        }

        try {

            SourceWriter sourceWriter = getSourceWriter(logger, context, classType);
            if (sourceWriter != null) {
                JClassType ginType = getGinjectorType(logger, typeOracle, classType, ginMapperType);
                List<JClassType> codeSplitPlaces = getCodeSplitPlaces(logger, typeOracle, typeName);
                writeGetMethod(logger, sourceWriter);
                writeGetActivityMethod(logger, sourceWriter, codeSplitPlaces, ginType);

                sourceWriter.commit(logger);
                logger.log(TreeLogger.DEBUG, "Done Generating source for "
                        + classType.getName(), null);
            }

        } catch (NotFoundException e) {
            logger.log(TreeLogger.ERROR, "Error Generating source for " + typeName, e);
            throw new UnableToCompleteException();
        }

        return classType.getQualifiedSourceName() + NamePostfix;
    }

    public SourceWriter getSourceWriter(TreeLogger logger, GeneratorContext context, JClassType classType) {

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

    private JClassType getGinjectorType(TreeLogger logger, TypeOracle typeOracle, JClassType classType, JClassType ginMapperType) throws UnableToCompleteException, NotFoundException {

        for (JClassType implInt: classType.getImplementedInterfaces()) {
            if (implInt.isAssignableTo(ginMapperType) && implInt.isParameterized() != null) {
                JClassType ginType = implInt.isParameterized().getTypeArgs()[0];
                return ginType;
            }
        }
        logger.log(Type.ERROR, "Must provide a must provide a generic type on CodeSplitGinMapper");
        throw new UnableToCompleteException();
    }

    private List<JClassType> getCodeSplitPlaces(TreeLogger logger, TypeOracle typeOracle, String mapperType) throws NotFoundException, UnableToCompleteException {
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
        sourceWriter.println("callback.onFailure(reason);");
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
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("});");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    private void writeGetActivityMethod(TreeLogger logger, SourceWriter sourceWriter, List<JClassType> codeSplitPlaces, JClassType ginType) throws NotFoundException, UnableToCompleteException {
        sourceWriter.println("private static " + ginType.getQualifiedBinaryName() + " ginjector;");
        sourceWriter.println("public " + ginType.getQualifiedBinaryName() + " getGinjector() {");
        sourceWriter.indent();
        sourceWriter.println("return ginjector;");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
        sourceWriter.println("public Activity getActivity(final SlottedPlace place) {");
        sourceWriter.indent();
        sourceWriter.println("if (ginjector == null) {");
        sourceWriter.indent();
        sourceWriter.println("ginjector = GWT.create(" + ginType.getQualifiedBinaryName() + ".class);");
        sourceWriter.outdent();
        sourceWriter.println("}");

        for (JClassType place: codeSplitPlaces) {
            generateIf(logger, sourceWriter, place, ginType);
        }

        sourceWriter.println("return null;");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    private void generateIf(TreeLogger logger, SourceWriter sourceWriter, JClassType placeType, JClassType ginType) throws NotFoundException, UnableToCompleteException {
        PlaceActivity annotation = placeType.getAnnotation(PlaceActivity.class);
        if (annotation == null || annotation.value() == null) {
            logger.log(TreeLogger.ERROR, "@PlaceActivity not defined on:" + placeType);
            throw new UnableToCompleteException();
        }
        Class<? extends Activity> activityClass = annotation.value();

        String methodName = "get" + activityClass.getSimpleName();
        try {
            ginType.getMethod(methodName, new JType[0]);
        } catch (NotFoundException e) {
            logger.log(TreeLogger.ERROR, ginType + " needs to implement method:" + methodName + "()");
            throw new UnableToCompleteException();
        }

        sourceWriter.println("if (place instanceof " + placeType.getQualifiedSourceName() + ") {");
        sourceWriter.indent();
        sourceWriter.println("return ginjector." + methodName + "();");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

}
