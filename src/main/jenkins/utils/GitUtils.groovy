package main.jenkins.utils

/**
 * Clase que contiene métodos para trabajar con Git
 */
class GitUtils {
    /**
     * Método que clona un repositorio de Git
     * @param url URL del repositorio
     * @param branch Rama del repositorio
     */
    static void cloneRepository(String branch, String url) {
        sh "git clone -b ${branch} ${url}"
    }   
}