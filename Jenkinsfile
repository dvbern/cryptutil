pipeline {
    agent any
    options {
    	buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '1'))
    }
    tools {
        maven 'Maven_3.5.0'
        jdk 'JDK_1.8_152'
    }
    stages {
        stage ('Build') {
        	steps {
        		withMaven(
					maven: 'Maven_3.5.0',
					jdk: 'JDK_1.8_152'
				) {
					sh 'mvn -U -Pdvbern.oss clean deploy'
				}
			}
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}
