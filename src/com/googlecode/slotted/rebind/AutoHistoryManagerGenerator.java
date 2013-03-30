package com.googlecode.slotted.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.googlecode.slotted.client.AutoHistoryMapper;
import com.googlecode.slotted.client.SlottedPlace;

import java.io.PrintWriter;

public class AutoHistoryManagerGenerator extends Generator {
    private static String NamePostfix = "Gen";

    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException
    {


        TypeOracle typeOracle = context.getTypeOracle();
        JClassType clazz = typeOracle.findType(typeName);

        if (clazz == null) {
            throw new UnableToCompleteException();
        }

        try {

            SourceWriter sourceWriter = getSourceWriter(clazz, context, logger);
            if (sourceWriter != null) {
                writeInitMethod(typeOracle, sourceWriter);

                sourceWriter.commit(logger);
                logger.log(TreeLogger.DEBUG, "Done Generating source for "
                        + clazz.getName(), null);
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new UnableToCompleteException();
        }

        return clazz.getQualifiedSourceName() + NamePostfix;
    }

    public SourceWriter getSourceWriter(JClassType classType, GeneratorContext context, TreeLogger logger) {

        String packageName = classType.getPackage().getName();
        String simpleName = classType.getSimpleSourceName() + NamePostfix;
        ClassSourceFileComposerFactory composer =
                new ClassSourceFileComposerFactory(packageName, simpleName);

        String implementName = AutoHistoryMapper.class.getName();
        composer.setSuperclass(implementName);
        composer.addImport(GWT.class.getCanonicalName());

        PrintWriter printWriter = context.tryCreate(logger, packageName,simpleName);

        if (printWriter == null) {
            return null;
        } else {
            SourceWriter sw = composer.createSourceWriter(context, printWriter);
            return sw;
        }

    }

    private void writeInitMethod(TypeOracle typeOracle, SourceWriter sourceWriter)
            throws NotFoundException
    {
        String markerName = SlottedPlace.class.getName();
        JClassType placeType = typeOracle.getType(markerName);

        sourceWriter.println("protected void init() {");
        sourceWriter.indent();

        JClassType[] types = typeOracle.getTypes();

        for (int i = 0; i < types.length; i++) {
            if (!types[i].isAbstract() && types[i].isDefaultInstantiable() &&
                    types[i].isAssignableTo(placeType))
            {
                sourceWriter.println("registerPlace(" + types[i].getQualifiedSourceName() + ".class);");
            }
        }

        sourceWriter.outdent();
        sourceWriter.println("}");
    }

}
