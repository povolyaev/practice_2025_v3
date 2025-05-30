pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    def scmVars = checkout([
                        $class: 'GitSCM',
                        branches: [[name: env.BRANCH_NAME]],
                        extensions: [
                            [$class: 'LocalBranch', localBranch: env.BRANCH_NAME],
                            [$class: 'CheckoutOption', timeout: 30]
                        ],
                        userRemoteConfigs: [[
                            url: 'https://github.com/povolyaev/practice_2025_v3.git'
                        ]]
                    ])
                    echo "Detected branch: ${env.BRANCH_NAME}"
                }
            }
        }

        stage('Build Code') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            when {
                expression { env.BRANCH_NAME.startsWith("feature/") }
            }
            steps {
                bat 'mvn test'
                junit testResults: '**/surefire-reports/*.xml'
            }
        }

        stage('Static Analysis') {
            when {
                expression { env.BRANCH_NAME == "develop" }
            }
            steps {
                bat 'mvn checkstyle:check'
            }
        }

        stage('Code Coverage Report') {
            steps {
                // Запуск Maven для генерации отчета Jacoco
                bat 'mvn jacoco:report'

                // Вывод отчета покрытия в Jenkins UI (требуется плагин JaCoCo)
                jacoco(
                    execPattern: '**/target/jacoco.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java'
                )
            }
        }

        stage('Install Artifacts') {
            steps {
                bat 'mvn install -DskipTests'
            }
        }

        stage('Check Coverage Requirements') {
            steps {
                bat 'mvn jacoco:check'
            }
        }

        stage('Deploy Artifact') {
            steps {
                    script {
                        // Заменяем '/' на '-', чтобы имя каталога было корректным для Windows
                        def branchName = env.BRANCH_NAME.replace('/', '-')
                        def targetDir = "C:\\artifacts\\${branchName}"

                        bat """
                            if not exist "${targetDir}" (
                                mkdir "${targetDir}"
                            )
                            copy /Y "app-module\\target\\*.jar" "${targetDir}\\"
                        """
                    }
                    archiveArtifacts artifacts: 'app-module/target/*.jar', fingerprint: true
            }
        }
    }
}
