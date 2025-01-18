//import main.jenkins.utils.FileUtils
//import main.jenkins.utils.GitUtils


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
            sh{
                echo "Clonando el repositorio..."
                git branch: 'develop', url: "https://github.com/Zoimback/auto-piops.git"
            }
        }

        stage('Build') {
            sh{
                echo "Ejecutando build..."
            }
        }

        stage('Deploy') {
            sh {
                echo "Desplegando en producción..."
            }
        }
    }
}