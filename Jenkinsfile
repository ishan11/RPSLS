'''
################# USER DEFINED VARIABLES###############################
'''
def CF_API ="api.sys.apbg.apcf.io"
def CF_HOSTNAME="mmp-be"
def CF_DOMAIN="apps.apbg.apcf.io"
def CF_ORG_DEV="apbg-dev"
def CF_ORG_PROD="apbg-prod"
def CF_SPACE="cloudworks"
def JOB_NAME ="${env.JOB_NAME}".tokenize('/')[0]
def BRANCH ="${env.JOB_NAME}".tokenize('/')[1]
def JENKINS_NOTIFICATIONS_TO ="manu.chandrasekhar@accenture.com"
'''
################# USER DEFINED VARIABLES###############################
'''


pipeline {
  agent any
    //environment {
      //    scannerHome = tool 'sonarscanner'
      //}
    tools {
      maven "maven"
    }
  options {
    disableConcurrentBuilds()
    timeout(time: 1, unit: 'HOURS')
    skipDefaultCheckout(false)
    parallelsAlwaysFailFast()
    buildDiscarder(logRotator(numToKeepStr: '15'))
  }
    stages {
        stage('Initialize') {
          steps {
            echo"add steps to verify environment"
		    sh "cd RPS-rest"
		    sh "mvn clean package -DskipTests=true"
		    sh "ls -al"
          }
        }

        stage('Build artifact') {
          steps {
            sh "cd RPS-rest"
		    sh "mvn clean package -DskipTests=true"
		    sh "ls -al"
            }
        }
     //   stage('Sonar Analysis') {
       //     steps {
         //     withSonarQubeEnv('sonar') {
           //       sh "${scannerHome}/bin/sonar-scanner  "
             // }
            //}
        //}

        stage('Deploy Code to PCF'){
            steps{
             withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                          credentialsId   : 'PCF_CREDENTIALS',
                          usernameVariable: 'USERNAME',
                          passwordVariable: 'PASSWORD']]) {

                        sh "cf login -a $CF_API -u $USERNAME -p $PASSWORD"
               			sh "cf target -o $CF_ORG_PROD -s $CF_SPACE"
                        sh 'cf push -f ./manifest-prod.yml'
            }
                    }
        }

    }
  post{
    success{
      echo"SUCCESSFUL BUILD"
    }
    failure{
      echo "BUILD FAILED"
    }
    always{
      junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
      deleteDir()
    }
  }
}