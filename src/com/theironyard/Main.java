package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList();
        Spark.staticFileLocation("/public");
        Spark.init();
        Spark.get(
                "/posts",
                ((request1, response1) -> {
                    HashMap m = new HashMap();
                    m.put("name", users);
                    m.put("posts", users);
                    return new ModelAndView(m, "posts.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    User user = new User();
                    user.name = request.queryParams("username");
                    users.add(user);
                    response.redirect("/posts");
                    return("");

                })
        );
       Spark.post(
                "/create-post",
        );
    }
}
