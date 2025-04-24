import main.jenkins.utils.GitUtils

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

        

        stage('Checkout') {
            def gitUtils = new GitUtils(this) //Contexto de la pipeline
            def url = scm?.getUserRemoteConfigs()?.first()?.getUrl() ?: 'Desconocido'
            def rama = env.BRANCH_NAME
            gitUtils.cloneRepository(rama, url)
        }
        stage('Printeo'){
            echo "Esto tira"
            echo "Funciona"
        }

        stage('Clean') {
            cleanWs() // Limpia el workspace
        }
        
    }
}