package com.9homme.gitlabMRStat.controller

import com.9homme.gitlabMRStat.helper.GitlabStatHelper
import com.9homme.gitlabMRStat.helper.UserCache
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IndexController(private val gitlabStatHelper: GitlabStatHelper, private val userCache: UserCache) {

    val title = "LINE MAN x Wongnai x Gitlab Engineering Stats"

    @GetMapping("/index.html")
    fun index(@RequestParam username: String?, model: Model): String {
        model["title"] = title
        if(username?.length?:0 > 0){
            model["result"] = gitlabStatHelper.getStats(username!!)
        }
        model["username"] = username?:""
        model["users"] = userCache.getAllUsers()
        return "index"
    }

}