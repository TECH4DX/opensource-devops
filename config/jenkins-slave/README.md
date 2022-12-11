## 目的
准备好jenkins-slave的镜像环境，优化cicd时下载额外资源，导致耗时过长的问题

## 步骤
### 配置文件放置
1. k8s 配置文件

    a. 将`/root/.kube/`下的的config复制到和Dockerfile一致的目录下。
    
    b. 复制kubectl 命令集到和Dockerfile一致的目录下。（cp ）
  
  
### docker build

### docker push