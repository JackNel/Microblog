package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap();
        ArrayList<Post> posts = new ArrayList();
        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");
                    if (username == null) {
                        return new ModelAndView(new HashMap(),"/not-logged-in.html");
                    }
                    HashMap m = new HashMap();
                    m.put("username", username);
                    m.put("posts", posts);
                    return new ModelAndView(m, "/logged-in.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    Session session = request.session();
                    String username = request.queryParams("username");
                    session.attribute("username", username);
                    if (users.get(username) == null) {
                        User user = new User();
                        user.username = username;
                        users.put(username, user);
                    }
                    response.redirect("/");
                    return "";
                })
        );
       Spark.post(
               "/create-post",
               ((request, response) -> {
                   Session session = request.session();
                   String username = session.attribute("username");
                   Post post = new Post();
                   post.id = posts.size() + 1;
                   post.text = request.queryParams("post");
                   posts.add(post);
                   response.redirect("/");
                   return ("");
               })
       );
        Spark.post(
                "/delete-post",
                ((request, response) -> {
                    String id = request.queryParams("postid");
                    try {
                        int idNum = Integer.valueOf(id);
                        posts.remove(idNum-1);
                        for (int i = 0; i < posts.size(); i++) {
                            posts.get(i).id = i + 1;
                        }
                    } catch (Exception e){

                    }
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/edit-post",
                ((request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    String editId = request.queryParams("editid");
                    String edit = request.queryParams("edit");
                    try {
                        int editIdNum = Integer.valueOf(editId);
                        Post editPost = posts.get(editIdNum - 1);
                        editPost.text = edit;
                    } catch (Exception e) {

                    }
                    response.redirect("/");
                    return "";
                })
        );
    }
}
