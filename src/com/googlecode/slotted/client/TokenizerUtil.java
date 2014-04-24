package com.googlecode.slotted.client;

import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

/**
 * A utility class used by {@link AutoTokenizer}.
 */
public class TokenizerUtil {

    private LinkedList<String> parameters;

    public static TokenizerUtil build() {
        return new TokenizerUtil();
    }

    public static TokenizerUtil extract(String token) {
        return new TokenizerUtil(token);
    }

    private TokenizerUtil() {
        parameters = new LinkedList<String>();
    }

    private TokenizerUtil(String token) {
        String[] params = token.split("&");
        parameters = new LinkedList<String>();
        for (String param: params) {
            if ("#".equals(param)) {
                parameters.add(null);

            } else {
                param = URL.decodePathSegment(param);
                parameters.add(param);
            }
        }
    }

    public String tokenize() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String param: parameters) {
            if (!first) {
                sb.append("&");
            } else {
                first = false;
            }
            if (param == null) {
                sb.append("#");
            } else {
                sb.append(URL.encodePathSegment(param));
            }
        }

        return sb.toString();
    }

    public boolean hasMore() {
        return !parameters.isEmpty();
    }

    public String get() {
        if (!parameters.isEmpty()) {
            return parameters.removeFirst();
        }
        return "";
    }

    public byte getbyte() {
        if (!parameters.isEmpty()) {
            return Byte.parseByte(parameters.removeFirst());
        }
        return 0;
    }

    public short getshort() {
        if (!parameters.isEmpty()) {
            return Short.parseShort(parameters.removeFirst());
        }
        return 0;
    }

    public int getint() {
        if (!parameters.isEmpty()) {
            return Integer.parseInt(parameters.removeFirst());
        }
        return 0;
    }

    public float getfloat() {
        if (!parameters.isEmpty()) {
            return Float.parseFloat(parameters.removeFirst());
        }
        return 0f;
    }

    public double getdouble() {
        if (!parameters.isEmpty()) {
            return Double.parseDouble(parameters.removeFirst());
        }
        return 0d;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public boolean getboolean() {
        if (!parameters.isEmpty()) {
            return Boolean.parseBoolean(parameters.removeFirst());
        }
        return false;
    }

    public char getchar() {
        if (!parameters.isEmpty()) {
            return parameters.removeFirst().charAt(0);
        }
        return '\u0000';
    }

    public Byte getByte() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return new Byte(param);
            }
        }
        return null;
    }

    public Short getShort() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return new Short(param);
            }
        }
        return null;
    }

    public Integer getInteger() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return new Integer(param);
            }
        }
        return null;
    }

    public Long getLong() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return new Long(param);
            }
        }
        return null;
    }

    public Float getFloat() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return new Float(param);
            }
        }
        return null;
    }

    public Double getDouble() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return new Double(param);
            }
        }
        return null;
    }

    public Boolean getBoolean() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return Boolean.valueOf(param);
            }
        }
        return null;
    }

    public Character getCharacter() {
        if (!parameters.isEmpty()) {
            String param = parameters.removeFirst();
            if (param != null && !param.isEmpty()) {
                return param.charAt(0);
            }
        }
        return null;
    }

    public Date getDate() {
        if (!parameters.isEmpty()) {
            String d = parameters.removeFirst();
            if(d != null && d.trim().length() > 0) {
                return DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).parse(d);
            }
        }
        return null;
    }

    public Timestamp getTimestamp() {
        if (!parameters.isEmpty()) {
            String ts = parameters.removeFirst();
            if(ts != null && ts.trim().length() > 0) {
                return new Timestamp(DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).parse(ts).getTime());
            }
        }
        return null;
    }

    public TokenizerUtil add(String param) {
        parameters.add(param);
        return this;
    }

    public TokenizerUtil add(Object param) {
        if (param == null) {
            parameters.add("");
        } else {
            parameters.add(param.toString());
        }
        return this;
    }

    public TokenizerUtil add(int param) {
        parameters.add("" + param);
        return this;
    }

    public TokenizerUtil add(float param) {
        parameters.add("" + param);
        return this;
    }

    public TokenizerUtil add(double param) {
        parameters.add("" + param);
        return this;
    }

    public TokenizerUtil add(boolean param) {
        parameters.add("" + param);
        return this;
    }

    public TokenizerUtil add(char param) {
        parameters.add("" + param);
        return this;
    }

    public TokenizerUtil add(Date param) {
        if(param != null) {
            parameters.add(DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).format(param));
        } else {
            parameters.add("");
        }
        return this;
    }

    public TokenizerUtil add(Timestamp param) {
        if(param != null) {
            parameters.add(DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).format(param));
        } else {
            parameters.add("");
        }
        return this;
    }

}
