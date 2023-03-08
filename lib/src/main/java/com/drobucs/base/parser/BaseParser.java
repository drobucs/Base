package com.drobucs.base.parser;
@SuppressWarnings("unused")
public abstract class BaseParser<T> implements Parser<T> {
    public static final char END = 0;
    protected CharSource source;
    private char ch;

    protected BaseParser(final CharSource source) {
        startParse(source);
    }

    protected void startParse(final CharSource source) {
        if (source == null) {
            return;
        }
        this.source = source;
        take();
    }

    protected char getCh() {
        return ch;
    }

    protected boolean isEnd() {
        return ch == END;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }


    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String expected) {
        for (int i = 0; i < expected.length(); ++i) {
            expect(expected.charAt(i));
        }
    }

    protected RuntimeException error(final Object message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch) && !isEnd()) {
            take();
        }
    }
    protected String getToken() {
        StringBuilder sb = new StringBuilder();
        while (!Character.isWhitespace(ch) && !isEnd()) {
            sb.append(ch);
            take();
        }
        return sb.toString();
    }
    protected void skipSymbols() {
        while (!Character.isWhitespace(ch) && !isEnd()) {
            take();
        }
    }
    protected void skipTo(final char to) {
        while (ch != to && !isEnd()) {
            take();
        }
        take();
    }

    protected String collectTo(char ch) {
        StringBuilder sb = new StringBuilder();
        while (getCh() != ch && !isEnd()) {
            sb.append(getCh());
            take();
        }
        return sb.toString();
    }
}
