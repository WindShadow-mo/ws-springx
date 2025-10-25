# 风的影子（WindShadow）

**Blog**: https://blog.windshadow.cn

**ws-springx doc**: https://blog.windshadow.cn/ws-springx

### 如何在项目中引用此依赖

**步骤 1：** 在maven的 `settings.xml` 添加 GitHub 认证：

```xml

<settings>
    <servers>
        <server>
            <id>github</id>
            <username>你的 GitHub 用户名</username>
            <password>你的 GitHub Personal Access Token</password>
        </server>
    </servers>
</settings>
```

**步骤 2：** 在项目的 `pom.xml` 添加仓库配置：

```xml

<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/WindShadow-mo/ws-springx</url>
    </repository>
</repositories>
```

**步骤 3：** 添加依赖：

```xml

<dependency>
    <groupId>ws.spring</groupId>
    <artifactId>ws-springx-common</artifactId>
    <version>${版本号}</version>
</dependency>
```

注意：server id必须与repository id一致。如果配置了maven镜像地址，需要忽略id为github（实际取决你配置的server id）的仓库，如下示例

```
 <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>*,!github</mirrorOf>
     <name>阿里云公共仓库</name>
     <url>https://maven.aliyun.com/repository/public</url>
 </mirror>
```