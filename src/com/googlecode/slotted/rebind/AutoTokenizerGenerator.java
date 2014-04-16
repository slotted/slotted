package com.googlecode.slotted.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.googlecode.slotted.client.AutoTokenizer;
import com.googlecode.slotted.client.GlobalParameter;
import com.googlecode.slotted.client.PlaceParameters;
import com.googlecode.slotted.client.SlottedPlace;
import com.googlecode.slotted.client.TokenizerParameter;
import com.googlecode.slotted.client.TokenizerUtil;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AutoTokenizerGenerator extends Generator {
    private static String NamePostfix = "Tokenizer";

    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException
    {
        try {
            TypeOracle typeOracle = context.getTypeOracle();
            JClassType placeType = getPlaceType(typeOracle, typeName);
            if (placeType == null) {
                throw new UnableToCompleteException();
            }

            LinkedList<JField> tokenParams = new LinkedList<JField>();
            LinkedList<JField> globalParams = new LinkedList<JField>();
            LinkedList<JField> equalsParams = new LinkedList<JField>();

            Set<? extends JClassType> allTypes = placeType.getFlattenedSupertypeHierarchy();
            for (JClassType type: allTypes) {
                JField[] fields = type.getFields();
                for (JField field: fields) {
                    for (Annotation annotation: field.getAnnotations()) {
                        if (annotation instanceof TokenizerParameter) {
                            tokenParams.add(field);
                            if (((TokenizerParameter) annotation).useInEquals()) {
                                equalsParams.add(field);
                            }
                            break;

                        } else if (annotation instanceof GlobalParameter) {
                            globalParams.add(field);
                            if (((GlobalParameter) annotation).useInEquals()) {
                                equalsParams.add(field);
                            }
                            break;
                        }
                    }
                }
            }

            SourceWriter sourceWriter = getSourceWriter(placeType, context, logger);
            if (sourceWriter != null) {
                writeConstructor(sourceWriter, placeType);
                writeAccessors(sourceWriter, tokenParams, placeType);
                writeAccessors(sourceWriter, globalParams, placeType);
                writeGlobalExtractor(sourceWriter, globalParams, placeType);
                writeGlobalSetter(sourceWriter, globalParams, placeType);
                writeGetToken(sourceWriter, tokenParams, placeType);
                writeGetPlace(sourceWriter, tokenParams, placeType);
                writeEquals(sourceWriter, equalsParams, placeType);

                sourceWriter.commit(logger);
                logger.log(TreeLogger.DEBUG, "Done Generating source for " + placeType.getName(), null);
            }

            return placeType.getQualifiedSourceName() + NamePostfix;
        } catch (NotFoundException e) {
            logger.log(TreeLogger.ERROR, "Error Generating source for " + typeName, e);
            throw new UnableToCompleteException();
        }

    }

    private JClassType getPlaceType(TypeOracle typeOracle, String typeName)
            throws NotFoundException
    {
        JClassType placeType = null;
        JClassType placeBaseType = typeOracle.getType(SlottedPlace.class.getName());
        JClassType tokenizerBaseType = typeOracle.getType(AutoTokenizer.class.getName());
        JClassType type = typeOracle.findType(typeName);

        if (type.isAssignableTo(placeBaseType)) {
            placeType = type;
        } else if (type.isAssignableTo(tokenizerBaseType)) {
            JClassType[] interfaces = type.getImplementedInterfaces();
            JClassType[] typeArgs = interfaces[0].isParameterized().getTypeArgs();
            placeType = typeArgs[0];
        }

        return placeType;
    }

    public SourceWriter getSourceWriter(JClassType classType, GeneratorContext context, TreeLogger logger) {

        String packageName = classType.getPackage().getName();
        String simpleName = classType.getSimpleSourceName() + NamePostfix;
        ClassSourceFileComposerFactory composer =
                new ClassSourceFileComposerFactory(packageName, simpleName);

        composer.addImplementedInterface(AutoTokenizer.class.getCanonicalName() +
                "<" + classType.getQualifiedSourceName() + ">");
        composer.addImport(GWT.class.getCanonicalName());
        composer.addImport(PlaceTokenizer.class.getCanonicalName());
        composer.addImport(PlaceParameters.class.getCanonicalName());
        composer.addImport(TokenizerUtil.class.getCanonicalName());
        composer.addImport(Date.class.getCanonicalName());
        composer.addImport(Timestamp.class.getCanonicalName());

        PrintWriter printWriter = context.tryCreate(logger, packageName,simpleName);

        if (printWriter == null) {
            return null;
        } else {
            SourceWriter sw = composer.createSourceWriter(context, printWriter);
            return sw;
        }

    }

    private void writeConstructor(SourceWriter sourceWriter, JClassType placeType) {
        sourceWriter.println("public " + placeType.getSimpleSourceName() + NamePostfix + "() {");
        sourceWriter.indent();
        sourceWriter.println("com.googlecode.slotted.client.AutoTokenizer.tokenizers.put(" + placeType.getQualifiedSourceName() + ".class, this);");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    private void writeAccessors(SourceWriter sourceWriter, List<JField> fields, JClassType placeType) {
        for (JField field: fields) {
            writeAccessors(sourceWriter, field, placeType);
        }
    }

    private void writeAccessors(SourceWriter sourceWriter, JField field, JClassType placeType) {
        sourceWriter.println("private native void set" + field.getName() + "(" +
                placeType.getQualifiedSourceName() + " place, " +
                field.getType().getSimpleSourceName() + " value) /*-{");
        sourceWriter.println("    place.@" + field.getEnclosingType().getQualifiedSourceName() + "::" +
                field.getName() + " = value;");
        sourceWriter.println("}-*/;");

        sourceWriter.println("private native " + field.getType().getSimpleSourceName() + " get" +
                field.getName() + "(" + placeType.getQualifiedSourceName() + " place) /*-{");
        sourceWriter.println("    return place.@" + field.getEnclosingType().getQualifiedSourceName() + "::" +
                field.getName() + ";");
        sourceWriter.println("}-*/;");
        sourceWriter.println();
    }

    private void writeGlobalExtractor(SourceWriter sourceWriter, List<JField> fields, JClassType placeType) {
        sourceWriter.println("public void extractFields(PlaceParameters intoPlaceParameters, " +
                placeType.getQualifiedSourceName() +" place) {");
        sourceWriter.indent();
        for (JField field: fields) {
            sourceWriter.println("intoPlaceParameters.set(\"" + field.getName() + "\", get" +
                    field.getName() + "(place));");
        }
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
    }

    private void writeGlobalSetter(SourceWriter sourceWriter, List<JField> fields, JClassType placeType) {
        sourceWriter.println("public void fillFields(PlaceParameters placeParameters, " +
                placeType.getQualifiedSourceName() +" place) {");
        sourceWriter.indent();
        for (JField field: fields) {
            sourceWriter.println("set" + field.getName() + "(place, placeParameters.get" +
                                    getGetMethod(field) + "(\"" + field.getName() + "\"));");
        }
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
    }

    private void writeEquals(SourceWriter sourceWriter, List<JField> fields, JClassType placeType) {
        sourceWriter.println("public boolean equals(" + placeType.getQualifiedSourceName() +" p1, " +
                placeType.getQualifiedSourceName() +" p2) {");
        sourceWriter.indent();
        sourceWriter.println("String s1;");
        sourceWriter.println("String s2;");

        for (JField field: fields) {
            String fieldName = field.getName();
            if (field.getType().isPrimitive() != null) {
                sourceWriter.println("if (get" + fieldName + "(p1) != get" + fieldName + "(p2)) {");
            } else if ("java.lang.String".equals(field.getType().getQualifiedBinaryName())) {
                sourceWriter.println("s1 = get" + fieldName + "(p1) != null ? get" + fieldName + "(p1) : \"\";");
                sourceWriter.println("s2 = get" + fieldName + "(p2) != null ? get" + fieldName + "(p2) : \"\";");
                sourceWriter.println("if (!s1.equals(s2)) {");
            } else {
                sourceWriter.println("if (get" + fieldName + "(p1) != null ? !get" + fieldName +
                        "(p1).equals(get" + fieldName + "(p2)) : get" + fieldName + "(p2) != null) {");
            }
            sourceWriter.indent();
            sourceWriter.println("return false;");
            sourceWriter.outdent();
            sourceWriter.println("}");
        }

        sourceWriter.println("return true;");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
    }

    private void writeGetToken(SourceWriter sourceWriter, List<JField> fields, JClassType placeType) {
        sourceWriter.println("public String getToken(" +
                placeType.getQualifiedSourceName() + " place) {");
        if (fields.isEmpty()) {
            sourceWriter.println("    return \"\";");
        } else {
            sourceWriter.println("    TokenizerUtil builder = TokenizerUtil.build();");
            for (JField field: fields) {
                sourceWriter.println("     builder.add(get" + field.getName() + "(place));");
            }
            sourceWriter.println("    return builder.tokenize();");
        }
        sourceWriter.println("}");
        sourceWriter.println();
    }

    private void writeGetPlace(SourceWriter sourceWriter, List<JField> fields, JClassType placeType) {
        String placeString = placeType.getQualifiedSourceName();
        sourceWriter.println("public " + placeString + " getPlace(String token) {");
        sourceWriter.indent();
        sourceWriter.println(placeString + " place = GWT.create(" + placeString + ".class);");
        if (!fields.isEmpty()) {
            sourceWriter.println("TokenizerUtil extractor = TokenizerUtil.extract(token);");
            for (JField field: fields) {
                sourceWriter.println("if (extractor.hasMore()) {");
                sourceWriter.indent();
                sourceWriter.println("set" + field.getName() + "(place, extractor.get" +
                        getGetMethod(field) + "());");
                sourceWriter.outdent();
                sourceWriter.println("}");
            }
        }
        sourceWriter.println("return place;");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
    }

    private String getGetMethod(JField field) {
        String name = field.getType().getSimpleSourceName();
        if ("String".equals(name)) {
            return "";
        } else {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

    }
}
