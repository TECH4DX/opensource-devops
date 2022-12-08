package org.foo;


pipeline {
    agent {
        kubernetes {
            //cloud 'kubernetes'
            defaultContainer 'jenkins-slave'
            yaml '''
            kind: Pod
            spec:
              containers:
              
              - name: "jenkins-slave"
                image: harbor.mlops.pub/jenkins/jenkins-slave:v2
                imagePullPolicy: Always
                command:
                - sleep
                args:
                - 99d
                volumeMounts:
                - mountPath: "/var/run/docker.sock"
                  name: "volume-0"
                  readOnly: false
                - mountPath: "/root/repo_code_config/opendataology_serviceSet"
                  name: "volume-1"
                  readOnly: false
                - mountPath: "/home/jenkins/agent"
                  name: "workspace-volume"
                  readOnly: false
                  
              - name: "jnlp"
                image: "jenkins/inbound-agent:4.11-1-jdk11"
                resources:
                  limits: {}
                  requests:
                    memory: "256Mi"
                    cpu: "100m"
                volumeMounts:
                - mountPath: "/var/run/docker.sock"
                  name: "volume-0"
                  readOnly: false
                - mountPath: "/home/jenkins/agent"
                  name: "workspace-volume"
                  readOnly: false
                  
              volumes:
              - hostPath:
                  path: "/var/run/docker.sock"
                name: "volume-0"
              - hostPath:
                  path: "/root/repo_code_config/opendataology_serviceSet"
                name: "volume-1"
              - emptyDir:
                  medium: ""
                name: "workspace-volume"
    '''
        }
    }
    environment {
        gitee_source_namespace = "";
        gitee_pull_request_i_id = "";
        gitee_repo_name = "";
        github_repo_image_name = "";
        _version = createVersion();
        GIT_URL = "https://github.com/ZichengQu/Opendataology_ServiceSet.git";
        BRANCH = "main";
        AUTH_TOKEN = "jenkins-mlops";
    }

    stages {

        stage('拉取代码') {
            // when {
            //     environment name: 'MODE',value: 'DEPLOY'
            // }
            steps {
                script {
                    try {
                        checkout(
                                [$class  : 'GitSCM', doGenerateSubmoduleConfigurations: false, submoduleCfg: [], extensions: [[$class: 'CloneOption', depth: 1, noTags: false, reference: '', shallow: true]],
                                 branches: [[name: "$BRANCH"]], userRemoteConfigs: [[url: "${env.GIT_URL}", credentialsId: "${env.AUTH_TOKEN}"]]]
                        )
                        // // 定义全局变量
                        // env.PULL_TIME = sh(script: "echo `date +'%Y-%m-%d %H:%M:%S'`", returnStdout: true).trim() // 获取时间
                        // env.COMMIT_ID   = sh(script: 'git log --pretty=format:%h',  returnStdout: true).trim() // 提交ID
                        // env.TRACE_ID = sh(script: "echo `head -c 32 /dev/random | base64`",  returnStdout: true).trim() // 随机生成TRACE_ID
                        // env.COMMIT_USER = sh(script: 'git log --pretty=format:%an', returnStdout: true).trim() // 提交者
                        // env.COMMIT_TIME = sh(script: 'git log --pretty=format:%ai', returnStdout: true).trim() // 提交时间
                        // env.COMMIT_INFO = sh(script: 'git log --pretty=format:%s',  returnStdout: true).trim() // 提交信息
                        // env._VERSION = sh(script: "echo `date '+%Y%m%d%H%M%S'`" + "_${COMMIT_ID}" + "_${env.BUILD_ID}", returnStdout: true).trim() // 对应构建的版本 时间+commitID+buildID
                    } catch (exc) {
                        // 添加变量占位，以避免构建异常
                        // env.PULL_TIME   = "无法获取"
                        // env.COMMIT_ID   = "无法获取"
                        // env.TRACE_ID = "无法获取"
                        // env.COMMIT_USER = "无法获取"
                        // env.COMMIT_TIME = "无法获取"
                        // env.COMMIT_INFO = "无法获取"
                        // env.IMAGE_NAME  = "无法获取"
                        // env.REASON = "构建分支不存在或认证失败"
                        throw (exc)
                    }
                }
            }
        }


        stage('Hello') {
            steps {
                echo "Hello World";
                echo "The build number is ${env.BUILD_NUMBER}";
                sh "printenv";

                script {
                    if (env.giteeTargetNamespace
                            && env.giteePullRequestIid) {
                        gitee_source_namespace = "${giteeTargetNamespace}";
                        gitee_pull_request_i_id = "${giteePullRequestIid}";
                        echo "${gitee_source_namespace}";
                        echo "${gitee_pull_request_i_id}";
                    }

                    if (env.GIT_URL) {
                        echo "来来来转换了zhuan"
                        echo splitStr(env.GIT_URL);
                        gitee_repo_name = splitStr(env.GIT_URL);
                        github_repo_image_name = toLowerCase(gitee_repo_name);
                        echo = "${gitee_repo_name} ${github_repo_image_name}";
                    }

                    // cur_date = curDate();
                    cur_date = sh "echo | date +%s"

                }
            }
        }

        stage('conf load') {
            steps {
                script {
                    sh "pwd"
                    sh "ls"

                    sh "cp /root/repo_code_config/opendataology_serviceSet/db_conf.py ./main/conf/"
                    sh "ls ./main/conf"
                }
            }
        }

        // stage('SonarQube analysis') {
        //     steps {
        //         script {
        //             def scannerHome = tool 'sonar-devops';
        //             withSonarQubeEnv('sonarqube-scanner') { // If you have configured more than one global server connection, you can specify its name
        //                 sh "pwd";
        //                 sh "ls";

        //                 sh "ls ${scannerHome}";
        //                 sh "cat ${scannerHome}/bin/sonar-scanner";
        //                 sh "${scannerHome}/bin/sonar-scanner -X -Dsonar.projectKey=serviceSet";
        //             }
        //         }
        //     }
        // }


        // //前
        // stage('sonar load result'){
        //     steps{
        //         script{
        //                 timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
        //                     def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
        //                     if (qg.status != 'OK') {
        //                         error "Pipeline aborted due to quality gate failure: ${qg.status},${qg}"
        //                     }

        //                 }
        //         }
        //     }
        // }

        //后

        // 这里的hello2 是我加的，就是说明，这是stages下的第二个任务 ,就是在pipeline中加单行注释 用 // 就行
        // stage('add comment') {
        //     steps {
        //         addGiteeMRComment()
        //     }
        // }

        //  // Pod 中 docker test1
        // stage('docker test') {
        //     steps {
        //         sh "docker images"
        //     }
        // }

        //         // docke推包
        stage('docker build and push') {
            steps {
                script {
                    sh "ls";
                    // sh "ping www.baidu.com";
                    // sh "docker rmi harbor.internal.domain/event_retriever/test:v1";
                    sh "docker build -t harbor.internal.domain/library/${github_repo_image_name}/${github_repo_image_name}_${_version}:v1 .";
                    sh "docker login harbor.internal.domain -u admin -p OpenSource@2022";
                    sh "docker push harbor.internal.domain/library/${github_repo_image_name}/${github_repo_image_name}_${_version}:v1";
                }

            }
        }

    }

}

def strBlockCheck(str) {
    return str != null || str != "";
}

// def curDate()
// {
//     String str = "";
//     SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
//     Calendar lastDate = Calendar.getInstance();
//     str = sdf.format(lastDate.getTime());
//     Date date =sdf.parse(str);
//     str=date.getTime();
//     return str;
// }

def checkOut()
{

    try {
        checkout(
                [$class  : 'GitSCM', doGenerateSubmoduleConfigurations: false, submoduleCfg: [], extensions: [[$class: 'CloneOption', depth: 1, noTags: false, reference: '', shallow: true]],
                 branches: [[name: "$BRANCH"]], userRemoteConfigs: [[url: "${env.GIT_URL}", credentialsId: "${env.AUTH_TOKEN}"]]]
        )
        // // 定义全局变量
        // env.PULL_TIME = sh(script: "echo `date +'%Y-%m-%d %H:%M:%S'`", returnStdout: true).trim() // 获取时间
        // env.COMMIT_ID   = sh(script: 'git log --pretty=format:%h',  returnStdout: true).trim() // 提交ID
        // env.TRACE_ID = sh(script: "echo `head -c 32 /dev/random | base64`",  returnStdout: true).trim() // 随机生成TRACE_ID
        // env.COMMIT_USER = sh(script: 'git log --pretty=format:%an', returnStdout: true).trim() // 提交者
        // env.COMMIT_TIME = sh(script: 'git log --pretty=format:%ai', returnStdout: true).trim() // 提交时间
        // env.COMMIT_INFO = sh(script: 'git log --pretty=format:%s',  returnStdout: true).trim() // 提交信息
        // env._VERSION = sh(script: "echo `date '+%Y%m%d%H%M%S'`" + "_${COMMIT_ID}" + "_${env.BUILD_ID}", returnStdout: true).trim() // 对应构建的版本 时间+commitID+buildID
    } catch (exc) {
        // 添加变量占位，以避免构建异常
        // env.PULL_TIME   = "无法获取"
        // env.COMMIT_ID   = "无法获取"
        // env.TRACE_ID = "无法获取"
        // env.COMMIT_USER = "无法获取"
        // env.COMMIT_TIME = "无法获取"
        // env.COMMIT_INFO = "无法获取"
        // env.IMAGE_NAME  = "无法获取"
        // env.REASON = "构建分支不存在或认证失败"
        throw (exc)
    }
}

def createVersion() {
    // 定义一个版本号作为当次构建的版本，输出结果 20191210175842_69
    return new Date().format('yyyyMMddHHmmss') + "_${env.BUILD_ID}"
}

def toLowerCase(str) {
    return str.toLowerCase();
}

def splitStr(str) {

    try {
        tempStrSq = str.split("/");
        lastStr = tempStrSq[tempStrSq.length - 1];
        println "groovy 脚本" + str + "   " + tempStrSq + "  " + lastStr;
        lastStrTempSq = lastStr.split("\\.");
        //   println "result length:" + lastStrTempSq.length;
        println "result split" + lastStrTempSq + "  " + lastStrTempSq[0];
        return lastStrTempSq[0];

    } catch (e) {
        println "异常" + e;
    }
}