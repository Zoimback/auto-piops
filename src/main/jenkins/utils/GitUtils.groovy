package main.jenkins.utils

/**
 * Clase que contiene métodos para trabajar con Git
 */
class GitUtils {

    /**
     * Método que clona un repositorio de Git
     * @param branch Rama del repositorio
     * @param url URL del repositorio
     * @param credentialsId ID de las credenciales almacenadas en Jenkins
     */
    void cloneRepository(String branch, String url) {
        // Validar que los parámetros sean válidos
        if (!branch || !url || !credentialsId) {
            throw new IllegalArgumentException("Los parámetros branch, url y credentialsId son obligatorios")
        }

        // Checkout del repositorio
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [],
            submoduleCfg: [],
            userRemoteConfigs: [[
                url: url,
                //credentialsId: credentialsId  // Se comenta porque no se está utilizando. Por si se necesita en un futuro la autenticación 
            ]]
        ])
    }
}
