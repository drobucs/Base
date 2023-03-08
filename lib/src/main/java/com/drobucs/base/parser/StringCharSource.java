package com.drobucs.base.parser;

@SuppressWarnings("unused")
public class StringCharSource implements CharSource {
    protected final String str;
    protected int pos;

    public StringCharSource(String str) {
        this.str = str;
        pos = -1;
    }

    @Override
    public char next() {
        return str.charAt(++pos);
    }

    @Override
    public boolean hasNext() {
        return pos < str.length() - 1;
    }

    @Override
    public RuntimeException error(Object message) {
        return new RuntimeException(message.toString());
    }

    @Override
    public char getNext() {
        return str.charAt(pos + 1);
    }
}
