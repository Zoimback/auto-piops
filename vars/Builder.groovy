//import main.jenkins.utils.FileUtils
import main.jenkins.utils.GitUtils
import main.jenkins.utils.DockerUtils
import main.jenkins.data.DBconector

void call(){

    node('build'){        
        stage('Read parameters') {
           properties([
                parameters([
                    ])
                ])
        }

        def dockerUtils = new DockerUtils(this) //Contexto de la pipeline

        stage('Checkout') {
            def gitUtils = new GitUtils(this) //Contexto de la pipeline
            def url = scm?.getUserRemoteConfigs()?.first()?.getUrl() ?: 'Desconocido'
            def rama = env.BRANCH_NAME
            gitUtils.cloneRepository(rama, url)
        }

        stage('Build-Docker Image') {
            dockerUtils.buildImage("params['Imagen']", "${env.WORKSPACE}/Dockerfile", "${env.WORKSPACE}")
        }

        stage('Build-Docker Container') {
            dockerUtils.buildContainer("params['Imagen']", "params['Imagen']")
        }
        stage('Compilation'){
            echo 'Compilacion del codigo'
        }

        stage('Test Execution'){
            echo 'Test del codigo'
        }

        stage('Artifacts'){
            archiveArtifacts artifacts: 'ARTEFACTO.csv', allowEmptyArchive: true
        }

        stage('Delete-Docker Container') {
            dockerUtils.removeContainer("params['Imagen']")
        }

        stage('Delete-Docker Image') {
            dockerUtils.removeImage('api-sensor')
        }

        /*stage('Mail') {
            def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.removeImage('api-sensor')
        }

        stage('DB Connection') {
            def dbconector = new DBconector(this) //Contexto de la pipeline
            dbconector.executeQuery("SELECT * FROM audit")
        }*/

        stage('Clean') {
            cleanWs() // Limpia el workspace
        }
        
    }
}