package ns.maintainusers.stepdefinitions.util;

import ns.maintainusers.entity.User;

public class MockUser {
    public static User getMockUser() {
        return new User(null,"user1","fn","ln","e@t.c",'A',"dep");
    }
}
