package br.com.attornatus.pessoa.pessoa.model.exception;

public class PessoaNaoExisteException extends Exception {

    public PessoaNaoExisteException() {
        super("Essa pessoa não existe!");
    }
}
