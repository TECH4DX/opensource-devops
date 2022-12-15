package org.devops

import groovy.json.JsonSlurper;

/**
 * Created on 2022/12/15
 *
 * @author linjianshan
 */
class Compliance {
    def repo_license_legal
    def spec_license_legal
    def license_in_scope
    def repo_copyright_legal
}

class LicenseDesc {
    Boolean pass
    String result_code
    String notice
    def detail
    def is_legal
    def copyrigth
}


static def http_get(url, isJson = false) {
    def conn = new URL(url).openConnection()

    conn.doOutput = true
    def writer = new OutputStreamWriter(conn.outputStream)
    writer.flush()
    writer.close()

    if (isJson) {
        def json = new JsonSlurper()

        def result = json.parseText(conn.content.text)
        return result
    } else {
        return conn.content.text
    }

}

def codeLicenseCheck(Integer prId) {
    try {
        String url = "https://compliance.openeuler.org/sca?prUrl=https://gitee.com/ljsnb/event-retriever/pulls/" + String.valueOf(prId)
        def res = http_get(url, true)
        println(res)
    } catch (Exception e) {
        println("license扫描异常，prId：" + prId + "\n异常:" + e)
    }

}

codeLicenseCheck(27)
//
//def res = http_get("https://compliance.openeuler.org/sca?prUrl=https://gitee.com/ljsnb/event-retriever/pulls/27", true)
////def res = http_get("https://www.baidu.com", false)
//Compliance compliance = res
//map = res.properties
//metaPropertyValues = res.metaPropertyValues
//Map<String, Object> objectMap = res
//
//println("metaPropertyValues输出：" + metaPropertyValues)
//
//
//println("转map输出" + objectMap + " 单个key看看 " + objectMap.get("repo_license_legal"))
//throw new RuntimeException("我直接抛出异常")
//println("输出:" + map + "  " + compliance.license_in_scope)

