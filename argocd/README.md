### 规划
#### 两步计划：
平台能力建设（流程覆盖度、能力-集成多少工具） -> 支撑业务落地，代码质量和开发效率提升

1. 平台能力建设
    a. 流程覆盖度：
    
        ci: git pull request & git push git pr comment-> build -> test -> 
        scan & check -> result response
        
        cd(持续交付): 手动触发&git push master -> build -> test -> scan & check -> 
        build image -> push image
        
        cd(持续部署)：config git push -> deploy of k8s （cd流程提供两个环境,分别是 
        sit-env 和 online-env，这里分仓库存config触发不同环境的部署）
    b.  能力集成多少扫描工具，支持语言环境:
        
        语言：go、python、java
        工具：sonarqube、公司内部和公司支持开源的其他的团队的洞察
        
2. 支撑业务落地
    需要平台能力建设准备完毕后，和团队内部大佬进行沟通，根据业务需求提供基础平台能力。

#### 时间节点

   1. 当前能力：
       1. 平台能力建设目前所具备的能力：
       
            1. 支持 github、gitee 平台的ci基本流程,其中 build 、 test 有语言限制,scan&check欠缺
                a. 支持 build 的语言：go java
                b. 支持 test  的语言: go java
                c. scan&check 目前集成开源版本的sonnarquebe，能适配扫描的go、java、
                python、c++等,但是匹配的语言规范只有部分，需要额外自己完善语言规划规则，不过这个工具目前这是试点，不是正式会去使用的工具。
            2. cd (持续交付)：流程上全部支持，能支持生成不同唯一后缀的镜像。
            3. cd (持续部署)：流程上是基于k8s提供了两个环境，测试环境sit-env、线上环境online-env，
            两个环境使用命名空间区分。
            
       2. 支撑业务落地 -暂未开始。
   2. 问题代办：
       1. 洞察公司内部和公司支持开源的其他的团队的ci/cd平台的工具集成能力，实现集成能力和集成两个工具做实践。
       2. cicd流程中的中间件密钥接入需要加密处理，不能以环境变量，比如harbor的账号密码，webhook token（防止洪水攻击）等。
       3. 提供辅助文档（目前还没有任何协助文档）。
       4. 目前使用工具没有考虑高可用性，比如harbor、argo、pgSql、sonarQube都是单实例支撑，持久化没有nfs支持。
   3. 平台能力建设交付件：
       1. ci能力完善，支持2个的代码扫描工具，支持go、java、python三种语言的构建测试。
       2. 提供辅助文档。
       3. cd 持续交付和持续部署流程完善。
   4. 平台能力建设交付时间点：
   2022.12.30号
   5. 支撑业务落地试点交付：
   支撑两个完善的业务功能落地，能正常走ci/cd全流程
   6. 支撑业务落地交付试点时间点：
   2023.01.20号
   7. 团队内部项目全部落地该基础设施平台：
   2023.03.10号
   