import org.devops.*;

def call() {

    println "helle nihao i am vars"
}

def sayHello() {

    echo "groovy sat hello!!"
}

/**
 * 字符串大小写转换
 * @param str
 * @return
 */
def toLowerCase(String str) {
    echo "啥啊！！";
    def tools = new Tools();

    return tools.toLowerCase(str);

}

/**
 * 代码license扫描
 * @param prId
 * @return
 */
def codeLicenseCheck(prId) {
    def compliancePerformer = new CompliancePerformer()
    compliancePerformer.codeLicenseCheck(prId)
}
