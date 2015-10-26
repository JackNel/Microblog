package com.theironyard;

/**
 * Created by Jack on 10/26/15.
 */
public class User {
    String name;
    String password;

    @Override
    public String toString() {
        return (String.format("%s", name));
    }
}
