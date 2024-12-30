pipeline {
    agent any

    parameters {
        choice(name: 'ENTORNO', choices: ['desarrollo', 'producción'], description: 'Selecciona el entorno de despliegue')
    }

    stages {
        stage('Preparación') {
            steps {
                echo "Clonando el repositorio..."
                git branch: 'develop', url: 'https://github.com/Zoimback/auto-piops.git'
            }
        }

        stage('Compilación') {
            steps {
                echo "Ejecutando build..."
               
            }
        }

        stage('Pruebas') {
            steps {
                echo "Ejecutando tests..."
              
            }
        }

        stage('Despliegue') {
            when {
                expression { params.ENTORNO == 'producción' }
            }
            steps {
                echo "Desplegando en producción..."
                
            }
        }

        stage('Despliegue Desarrollo') {
            when {
                expression { params.ENTORNO == 'desarrollo' }
            }
            steps {
                echo "Desplegando en desarrollo..."
              
            }
        }

        stage('Registro en Base de Datos') {
            steps {
                echo "Registrando despliegue en la base de datos..."
                
            }
        }
    }

    post {
        success {
            echo 'Pipeline completada exitosamente.'
        }
        failure {
            echo 'Pipeline fallida. Revisa los logs.'
        }
    }
}

