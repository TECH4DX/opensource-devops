package org.devops

import groovy.json.JsonSlurper


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

def checkOut() {

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

// 将 {"age":18,"name":"Tom"} 字符串进行反序列化
def jsonSlurper = new JsonSlurper()

// 将字符串进行 json 反序列化操作 , 得到 map 集合
def jsonObject = jsonSlurper.parseText('{"age":18,"name":"Tom"}');
// 打印反序列化结果
println jsonObject

class Student {
    def name
    def age
}

class Cat {
    def name
    def age
}

//不支持跨文件
Student student = (Student) jsonObject
Cat cat = (Cat) jsonObject
println "${student.age} , ${cat.name}"