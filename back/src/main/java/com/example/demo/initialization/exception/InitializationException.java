package com.example.demo.initialization.exception;

import java.io.FileNotFoundException;

public class InitializationException extends RuntimeException {
    public InitializationException(Throwable e) {
        super("Erro durante a inicialização", e);
    }
}
