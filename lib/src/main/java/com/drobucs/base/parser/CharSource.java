package com.drobucs.base.parser;
@SuppressWarnings("unused")
public interface CharSource {
    char next();
    boolean hasNext();
    RuntimeException error(Object message);
    char getNext();
}
