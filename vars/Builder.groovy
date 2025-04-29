//import main.jenkins.utils.FileUtils
import main.jenkins.utils.GitUtils
import main.jenkins.utils.DockerUtils
import main.jenkins.data.DBconector

void call(){

    node('build'){        
        stage('Read parameters') {
           properties([
                parameters([
                    //choice(name: 'ENTORNO', choices: ['desarrollo', 'producción'], description: 'Selecciona el entorno de despliegue')
                    //choice(name: 'Rehacer Imagen', choices: ['Si', 'No'], description: 'Selecciona si quieres rehacer la imagen de docker'),
                    //choice(name: 'Imagen', choices: ['api-sensor', 'sensor'], description: 'Selecciona que imagen de docker quieres construir')
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

        stage('Delete-Docker Image') {
            
            dockerUtils.buildImage("params['Imagen']", "${env.WORKSPACE}/Dockerfile", "${env.WORKSPACE}") 
        }
        stage('Build-Docker Image') {
            dockerUtils.buildImage("params['Imagen']", "${env.WORKSPACE}/Dockerfile", "${env.WORKSPACE}")
        }

        stage('Build-Docker Container') {
            dockerUtils.buildContainer("params['Imagen']", "params['Imagen']")
        }

        stage('Delete-Docker Container') {
            dockerUtils.removeContainer("params['Imagen']")
        }

        stage('Delete-Docker Image') {
            //def dockerUtils = new DockerUtils(this) //Contexto de la pipeline
            dockerUtils.removeImage('api-sensor')
        }
        /*
        stage('DB Connection') {
            def dbconector = new DBconector(this) //Contexto de la pipeline
            dbconector.executeQuery("SELECT * FROM audit")
        }*/

        stage('Clean') {
            cleanWs() // Limpia el workspace
        }
        
    }
}
