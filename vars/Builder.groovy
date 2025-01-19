//import main.jenkins.utils.FileUtils
import main.jenkins.utils.GitUtils


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
            gitUtils.cloneRepository('develop', 'https://github.com/Zoimback/auto-piops.git')
        }

        stage('Build') {
            println "Ejecutando build..."   
        }

        stage('Deploy') {
            println "Desplegando en producción..."
        }

        stage('Clean') {
            CleanWs()
        }
    }
}