package main.jenkins.utils

/**
 * Clase que contiene métodos para el Log
 */

class LoggerUtils{


    def pipelineContext

    /**
    * Constructor que recibe el contexto de la pipeline
    * @param pipelineContext El contexto de la pipeline (this)
    */

    LoggerUtils(def pipelineContext) {
        this.pipelineContext = pipelineContext
    }


    /**
    * Método que imprime un mensaje en la consola - info
    * @param message Mensaje a imprimir
    */
    void info(String message) {
        pipelineContext.echo "${message}"
    }

    /**
    * Método que imprime un mensaje en la consola - warning
    * @param message Mensaje a imprimir
    */
    void warning(String message) {
        pipelineContext.echo "${message}"
    }

    /**
    * Método que imprime un mensaje en la consola - error
    * @param message Mensaje a imprimir
    */
    void error(String message) {
        pipelineContext.echo "${message}"
    }
}
