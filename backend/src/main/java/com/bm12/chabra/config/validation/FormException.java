package com.bm12.chabra.config.validation;


// Classe que mapeia um erro de validação de formulario

public class FormException {
    private String field;
    private String error;

    public FormException(String campo, String erro) {
        this.field = campo;
        this.error = erro;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
