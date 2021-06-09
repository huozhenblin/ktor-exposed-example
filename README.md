这个项目有较为完善的项目结构示例，然后对exposed的使用方式与ktor的整合基本涵盖。

已知问题：
1.转化为mysql数据库时日期会出现问题。
2.改变文件重启时会报错找不到协程类（未解决），暂时的解决方法gradle clear以下项目重启即可。




# ![RealWorld Example App](logo.png)

> ### [Kotlin-Ktor](https://github.com/kotlin/ktor) codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.


### [Demo](https://github.com/gothinkster/realworld)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)


This codebase was created to demonstrate a fully fledged fullstack application built with **[Kotlin-Ktor](https://github.com/kotlin/ktor)** including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the **[Kotlin-Ktor](https://github.com/kotlin/ktor)** community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# Build Status
![Build](https://github.com/dragneelfps/realworld-kotlin-ktor/workflows/Build/badge.svg?branch=master)

# How it works

- [h2](https://h2database.com/html/main.html) database
- [hikari](https://github.com/brettwooldridge/HikariCP) as JDBC connection pool
- [Exposed](https://github.com/JetBrains/Exposed/) as Kotlin SQL Framework
- [Jackson](https://github.com/FasterXML/jackson) for handling JSON

# Getting started

> Installation

1. Install h2 database. Default configuration uses server mode.
2. Run the gradle. :)

> Running

1. Start the h2 database
2. Run the gradle. :))
3. Check on [http://localhost:8080/api](http:localhost:8080/api), if using default configuration. 
4. Yay.

> Testing
1. ./gradlew build test
