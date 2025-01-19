//import main.jenkins.utils.FileUtils
import main.jenkins.utils.GitUtils
import main.jenkins.utils.DockerUtils


void call(){

    node('build'){        
        stage('Read parameters') {
           properties([
                parameters([
                    //choice(name: 'ENTORNO', choices: ['desarrollo', 'producción'], description: 'Selecciona el entorno de despliegue')
                    ])
                ])
        }

        stage('Checkout') {
            def gitUtils = new GitUtils(this) //Contexto de la pipeline
            gitUtils.cloneRepository('develop', 'https://github.com/Zoimback/api-sensor.git')
        }
        
        stage('Build-Docker Image') {
            def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.buildImage('api-sensor', "${env.WORKSPACE}/DockerFile")
        }

        stage('Build-Docker Container') {
            def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.buildContainer('api-sensor', 'api-sensor')
        }

        stage('Delete-Docker Container') {
            def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.removeContainer('api-sensor')
        }

        stage('Delete-Docker Image') {
            def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.removeImage('api-sensor')
        }

        stage('Clean') {
            cleanWs() // Limpia el workspace
        }
        
    }
}