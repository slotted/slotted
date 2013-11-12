package com.googlecode.slotted.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.junit.JUnitMessageQueue.ClientStatus;

/**
 * todo javadoc
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
            param = URL.decodePathSegment(param);
            parameters.add(param);
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
            if (param != null) {
                sb.append(URL.encodePathSegment(param));
            }
        }

        return sb.toString();
    }

    public boolean hasMore() {
        return !parameters.isEmpty();
    }

    public String get() {
        return parameters.removeFirst();
    }

    public int getInt() {
        return Integer.parseInt(parameters.removeFirst());
    }

    public long getLong() {
        return Long.parseLong(parameters.removeFirst());
    }

    public float getFloat() {
        return Float.parseFloat(parameters.removeFirst());
    }

    public double getDouble() {
        return Double.parseDouble(parameters.removeFirst());
    }

    public boolean getBoolean() {
        return Boolean.parseBoolean(parameters.removeFirst());
    }
    
    public Date getDate() {
    	String d = parameters.removeFirst();
    	if(d != null && d.trim().length() > 0) {
    		return DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).parse(d);
    	} else {
    		return null;
    	}
    }

    public Timestamp getTimestamp() {
    	String ts = parameters.removeFirst();
    	if(ts != null && ts.trim().length() > 0) {
    		return new Timestamp(DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).parse(ts).getTime());
    	} else {
    		return null;
    	}
    }

    public TokenizerUtil add(String param) {
        parameters.add(param);
        return this;
    }

    public TokenizerUtil add(int param) {
        parameters.add("" + param);
        return this;
    }

    public TokenizerUtil add(long param) {
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
