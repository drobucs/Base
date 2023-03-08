package com.drobucs.base.function;

import java.io.IOException;

@FunctionalInterface
public interface FunctionIOE<T, R> {
    R apply(T arg) throws IOException;
}
