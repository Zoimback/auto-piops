package main.jenkins.utils

/**
 * Clase que contiene métodos para trabajar con Git
 */
class GitUtils {


   def pipelineContext

    /**
     * Constructor que recibe el contexto de la pipeline
     * @param pipelineContext El contexto de la pipeline (this)
     */
    GitUtils(def pipelineContext) {
        this.pipelineContext = pipelineContext
    }


    /**
     * Método que clona un repositorio de Git
     * @param branch Rama del repositorio
     * @param url URL del repositorio
     * @param credentialsId ID de las credenciales almacenadas en Jenkins
     */
    void cloneRepository(String branch, String url) {
        // Validar que los parámetros sean válidos
        if (!branch || !url) {
            throw new IllegalArgumentException("Parameters branch and url are must be provided")
        }

        // Checkout del repositorio
        pipelineContext.checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [],
            submoduleCfg: [],
            userRemoteConfigs: [[
                url: url,
                //credentialsId: credentialsId  // Se comenta porque no se está utilizando. Por si se necesita la autenticación  en un futuro 
            ]]
        ])
    }
}
