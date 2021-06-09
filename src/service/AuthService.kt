package com.nooblabs.service

import com.nooblabs.models.RegisterUser
import com.nooblabs.models.UpdateUser
import com.nooblabs.models.User
import com.nooblabs.models.Users
import com.nooblabs.service.DatabaseFactory.dbQuery
import com.nooblabs.util.UserDoesNotExists
import com.nooblabs.util.UserExists
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import java.util.*

/*
* 用户及权限相关的service类
*
*
* */
class AuthService {
    /*
    * 注册用户
    * */
    suspend fun register(registerUser: RegisterUser): User {
        return dbQuery {
            val userInDatabase =
                User.find { (Users.username eq registerUser.user.username) or (Users.email eq registerUser.user.email) }
                    .firstOrNull()
            //这个异常是一个运行时异常。
            if (userInDatabase != null) throw UserExists()
            User.new {
                username = registerUser.user.username
                email = registerUser.user.email
                password = registerUser.user.password
            }
        }
    }
    //获取所有用户
    suspend fun getAllUsers(): List<User> {
        return dbQuery {
            //dao方式查询所有，将各行取出封装为list
            User.all().toList()
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return dbQuery {
            User.find { Users.email eq email }.firstOrNull()
        }
    }

    suspend fun getUserById(id: String): User {
        return dbQuery {
            getUser(id)
        }
    }
    //登陆
    suspend fun loginAndGetUser(email: String, password: String): User {
        return dbQuery {
            User.find { (Users.email eq email) and (Users.password eq password) }.firstOrNull()
                ?: throw UserDoesNotExists()
        }
    }

    suspend fun updateUser(userId: String, updateUser: UpdateUser): User {
        return dbQuery {
            val user = getUser(userId)
            //使用dao方式进行更新。
            user.apply {
                email = updateUser.user.email ?: email
                password = updateUser.user.password ?: password
                username = updateUser.user.username ?: username
                image = updateUser.user.image ?: image
                bio = updateUser.user.bio ?: bio
            }
        }
    }


}
//使用dao方式查询会自动封装到对应的实体类
fun getUser(id: String) = User.findById(UUID.fromString(id)) ?: throw UserDoesNotExists()

fun getUserByUsername(username: String) = User.find { Users.username eq username }.firstOrNull()