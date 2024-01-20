package com.eservice.model.repository;

import com.eservice.model.IUser;

public class UserSingleton {
    private static UserSingleton uniqInstance;

    // Private constructor to prevent direct instantiation

    private IUser user;
    public static UserSingleton getInstance() {
        if (uniqInstance == null)
            uniqInstance = new UserSingleton();
        return uniqInstance;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }
}
