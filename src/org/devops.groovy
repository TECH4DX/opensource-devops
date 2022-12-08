package org.foo;


class User {
    public final String name

    User(String name) { this.name = name }

    String getName() { "Name: $name" }
}

def user = new User('Bob')
println user.name

test(user.name)

def test(str) {


    println Arrays.asList(str.split(":"))
}