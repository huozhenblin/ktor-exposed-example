package com.nooblabs.api

import com.nooblabs.models.LoginUser
import com.nooblabs.models.RegisterUser
import com.nooblabs.models.UpdateUser
import com.nooblabs.models.UserResponse
import com.nooblabs.service.AuthService
import com.nooblabs.util.SimpleJWT
import com.nooblabs.util.userId
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put

/*
*用户相关路由
*除了登陆注册接口
*其他需要权限
* */
fun Route.auth(authService: AuthService, simpleJWT: SimpleJWT) {

    /*
        Registration:
        POST /api/users
     */
    post("/users") {
        val registerUser = call.receive<RegisterUser>()
        val newUser = authService.register(registerUser)
        call.respond(UserResponse.fromUser(newUser, token = simpleJWT.sign(newUser.id)))
    }

    /*
        Authentication:
        POST /api/users/login
     */
    post("/users/login") {
        val loginUser = call.receive<LoginUser>()
        val user = authService.loginAndGetUser(loginUser.user.email, loginUser.user.password)
        call.respond(UserResponse.fromUser(user, token = simpleJWT.sign(user.id)))
    }

    authenticate {

        /*
            Get Current User
            GET /api/user
         */
        get("/user") {
            val user = authService.getUserById(call.userId())
            call.respond(UserResponse.fromUser(user))
        }

        /*
            Update User
            PUT /api/user
         */
        put("/user") {
            val updateUser = call.receive<UpdateUser>()
            //call.userId() jwt中获取对应id？
            val user = authService.updateUser(call.userId(), updateUser)
            call.respond(UserResponse.fromUser(user, token = simpleJWT.sign(user.id)))
        }

    }

    //For development purposes
    //Returns all users
    get("/users") {
        val users = authService.getAllUsers()
        //通过map方法，映射成user：{userinfo}的列表
        /*
        [
     {
    "user": {
      "email": "drag1@gmail.com",
      "token": "",
      "username": "drag1",
      "bio": "I work here too",
      "image": "https://image2.com"
    }
     },
     {
    "user": {
      "email": "drag1@gmail1.com",
      "token": "",
      "username": "drag2",
      "bio": "",
      "image": null
    }
    }
    ]*/
        call.respond(users.map { UserResponse.fromUser(it) })
    }

}