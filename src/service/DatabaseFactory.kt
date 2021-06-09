package com.nooblabs.service

import com.nooblabs.models.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        //创建数据库连接池
        Database.connect(hikari())
        //初始化数据库表
        transaction {
            create(Users, Followings, Articles, Tags, ArticleTags, FavoriteArticle, Comments, ArticleComment)

            //NOTE: Insert initial rows if any here
        }
    }

    //数据库链接池配置
    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl         = "jdbc:mysql://localhost/kotlin_example"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username        = "root"
            password        = "huo451545"
            maximumPoolSize = 10
        }
        return HikariDataSource(config)
    }
    //通用增删查改的通用函数保证，创建协程运行不阻塞主线程。
    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }

    suspend fun drop() {
        dbQuery { drop(Users, Followings, Articles, Tags, ArticleTags, FavoriteArticle, Comments, ArticleComment) }
    }
}