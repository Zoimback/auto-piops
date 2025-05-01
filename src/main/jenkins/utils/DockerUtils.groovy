package main.jenkins.utils

/**
 * Clase que contiene métodos para trabajar con Docker
 */

class DockerUtils {


    def pipelineContext

    /**
     * Constructor que recibe el contexto de la pipeline
     * @param pipelineContext El contexto de la pipeline (this)
     */

    DockerUtils(def pipelineContext) {
        this.pipelineContext = pipelineContext
    }


    /**
     * Método que construye una imagen de Docker
     * @param imageName Nombre de la imagen
     * @param dockerfilePath Ruta del Dockerfile
     * @param path Ruta del contexto de construcción
     */
    void buildImage(String imageName, String dockerfilePath, String path) {
        // Validar que los parámetros sean válidos
        if (!imageName || !dockerfilePath || !path) {
            throw new IllegalArgumentException("Parameters imageName, dockerfilePath and path are must be provided")
        }

        // Construir la imagen de Docker
        pipelineContext.sh "docker build --no-cache -t ${imageName} -f ${dockerfilePath} ${path}"
    }

    /**
     * Método que construye un contenedor de Docker
     * @param containerName Nombre del contenedor
     * @param imageName Nombre de la imagen
     */ 
    void buildContainer(String containerName, String imageName) {
        // Validar que los parámetros sean válidos
        if (!containerName || !imageName) {
            throw new IllegalArgumentException("Parameters containerName and imageName are must be provided")
        }

        // Construir el contenedor de Docker
        pipelineContext.sh "docker run -d --name ${containerName} ${imageName}"
    }

    /**
     * Método que elimina un contenedor de Docker
     * @param containerName Nombre del contenedor
     */
    void removeContainer(String containerName) {
        // Validar que los parámetros sean válidos
        if (!containerName) {
            throw new IllegalArgumentException("Parameter containerName must be provided")
        }

        // Eliminar el contenedor de Docker
        pipelineContext.sh "docker rm -f ${containerName}"
    }

    /**
     * Método que elimina una imagen de Docker
     * @param imageName Nombre de la imagen
     */
    void removeImage(String imageName) {
        // Validar que los parámetros sean válidos
        if (!imageName) {
            throw new IllegalArgumentException("Parameter imageName must be provided")
        }

        // Eliminar la imagen de Docker
        pipelineContext.sh "docker rmi -f ${imageName}"
    }

    /**
     * Método que ejecuta comandos en un contenedor de Docker
     * @param containerName nombre del contenedor
     * @param command Comando a ejecutar
     */
    void executeComand(String containerName, String command) {
        // Validar que los parámetros sean válidos
        if (!command || !containerName) {
            throw new IllegalArgumentException("Parameter command must be provided")
        }
        // Ejecutar el comando en el contenedor de Docker
        pipelineContext.sh "docker exec -i ${containerName} ${command}"
    }

    /**
     * Método que copia archivos desde el contenedor de Docker
     * @param containerName nombre del contenedor
     * @param file nombre del archivo a copiar
     * @param path ruta del contenedor
     */
    void copyCommand (String containerName, String path, String file) {
        // Validar que los parámetros sean válidos
        if (!file || !containerName || !path) {
            throw new IllegalArgumentException("Parameter command must be provided")
        }
        // Ejecutar el comando en el contenedor de Docker
        pipelineContext.sh "docker cp  ${containerName}:${path}${file} bin/${file}"
    }


    
}