//import main.jenkins.utils.FileUtils
import main.jenkins.utils.GitUtils
import main.jenkins.utils.DockerUtils
//import main.jenkins.data.DBconector

void call(){

    node('build'){        
        stage('Read parameters') {
           properties([
                parameters([
                    ])
                ])
        }

        def config

        def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
        try{

            stage('Checkout') {
                def gitUtils = new GitUtils(this) //Contexto de la pipeline
                def url = scm?.getUserRemoteConfigs()?.first()?.getUrl() ?: 'Desconocido'
                def rama = env.BRANCH_NAME
                gitUtils.cloneRepository(rama, url)
            }

            stage('Obtener Archivo de Configuracion') {
                echo 'Obteniendo archivo de configuracion'
                config = readYaml file: 'config/.ci-config.yml'

            }

            stage('Build-Docker Image') {
                dockerUtils.buildImage(config.name, "${env.WORKSPACE}/${config.image.workdir}", "${env.WORKSPACE}")
            }

            stage('Build-Docker Container') {
                dockerUtils.buildContainer(config.name, config.name)
            }
            stage('Compilation'){
                dockerUtils.executeComand(config.name, config.compilation.command)
            }

            stage('Test Execution'){
                dockerUtils.executeComand(config.name, config.test.command)
            }

            stage('Artifacts'){
                archiveArtifacts artifacts: config.compilation.file, allowEmptyArchive: true
            }
        }
        catch (Exception e) {
            echo "Error: ${e.message}"
            currentBuild.result = 'FAILURE'
        }
        finally {
            stage('Delete-Docker Container') {
                dockerUtils.removeContainer(config.name)
            }

            stage('Delete-Docker Image') {
                dockerUtils.removeImage(config.name)
            }
            stage('Clean') {
                cleanWs() // Limpia el workspace
            }
        }
        

       

        /*stage('Mail') {
            def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.removeImage('api-sensor')
        }

        stage('DB Connection') {
            def dbconector = new DBconector(this) //Contexto de la pipeline
            dbconector.executeQuery("SELECT * FROM audit")
        }*/

        
        
    }
}
