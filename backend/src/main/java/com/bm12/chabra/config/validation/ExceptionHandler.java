package com.bm12.chabra.config.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {


    /**
     * @param exception exceção lançada para qualquer inconsistencia de nos parametros
     *                  enviados como formulario para salvar um novo objeto no banco de dados
     * @return Lista de erros com a mensagem de erro e HTTP 400
     * */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public List<FormException> handle(MethodArgumentNotValidException exception) {
        return exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> new FormException(erro.getField(), erro.getDefaultMessage())).toList();
    }


    /**
     * @param exception exceção lançada quando o usuário passa um parametro e o mesmo
     *                  não existe no banco de dados
     * @return String com a mensagem de erro e HTTP 404
     * */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class})
    public String handle(NotFoundException exception) {

        // Retorna um erro
        return exception.getMessage();
    }


    /**
     * @param exception exceção lançada quando o usuário tenta inserir um usuário que já existe
     * @return String com a mensagem de erro e HTTP 409
     * */
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @org.springframework.web.bind.annotation.ExceptionHandler({AlreadyExistsException.class})
    public String handle(AlreadyExistsException exception) {

        // Retorna um erro
        return exception.getMessage();
    }


    /**
     * @param exception exceção lançada quando o usuário não está autorizado a acessar um recurso
     * @return String com a mensagem de erro e HTTP 401
     * */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler({UnauthorizedException.class})
    public String handle(UnauthorizedException exception) {

        // Retorna um erro
        return exception.getMessage();
    }

    /**
     * @param exception exceção lançada erros internos do servidor. Exemplo: Erro ao inserir um
     *                  usuário no banco de dados
     * @return String com a mensagem de erro e HTTP 500
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public String handle(RuntimeException exception) {

        // Retorna um erro
        return exception.getMessage();
    }

    /**
     * @param exception exceção generica para erros internos não mapeados
     * @return String com a mensagem de erro e HTTP 500
     * */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public String handle(Exception exception) {

        // Retorna um erro
        return exception.getMessage();
    }

}
