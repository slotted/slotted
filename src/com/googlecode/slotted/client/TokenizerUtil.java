package com.googlecode.slotted.client;

import com.google.gwt.http.client.URL;

import java.util.LinkedList;

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
}
