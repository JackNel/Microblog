package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList();
        ArrayList<Post> posts = new ArrayList();
        Spark.staticFileLocation("/public");
        Spark.init();
        Spark.get(
                "/posts",
                ((request1, response1) -> {
                    HashMap m = new HashMap();
                    m.put("name", users.get(0));
                    m.put("posts", posts);
                    return new ModelAndView(m, "posts.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    User user = new User();
                    user.name = request.queryParams("username");
                    user.password = request.queryParams("password");
                    users.add(user);
                    response.redirect("/posts");
                    return("");

                })
        );
       Spark.post(
                "/create-post",
                ((request, response) -> {
                    Post post = new Post();
                    post.text = request.queryParams("post");
                    posts.add(post);
                    response.redirect("/posts");
                    return("");
                })
        );
    }
}
