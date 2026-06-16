package com.daothiphuongthuy.models;

import java.util.ArrayList;

public class ListUserAccount {
    public static ArrayList<UserAccount> getAccounts() {
        ArrayList<UserAccount> accounts = new ArrayList<>();
        accounts.add(new UserAccount("admin", "123", "ADMIN", "Administrator", false));
        accounts.add(new UserAccount("employee", "123", "EMPLOYEE", "Employee User", false));
        return accounts;
    }

    public static UserAccount login(String user, String pass) {
        for (UserAccount uc : getAccounts()) {
            if (uc.getUsername().equalsIgnoreCase(user) && uc.getPassword().equals(pass)) {
                return uc;
            }
        }
        return null;
    }
}
