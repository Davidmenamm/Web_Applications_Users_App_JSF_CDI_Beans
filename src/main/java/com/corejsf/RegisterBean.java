package com.corejsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
// or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
// or import javax.faces.bean.SessionScoped;

@Named("register") // or @ManagedBean(name="login")
@SessionScoped
public class RegisterBean implements Serializable {
    private String username;
    private String password;
    private String confirmPassword;
    private Boolean passwordError = false;
    private Boolean registerError = false;
    @Inject
    private ServerBean server;
    @Inject
    private DatabaseBean db;
    @Inject
    private UserBean user;
    @Inject
    private HomeBean home;

    public String getUsername() { return username; }
    public void setUsername(String newValue) { username = newValue; }

    public String getPassword() { return password; }
    public void setPassword(String newValue) { password = newValue; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public Boolean getPasswordError() {        return passwordError;    }
    public void setPasswordError(Boolean passwordError) {        this.passwordError = passwordError;    }

    public Boolean getRegisterError() {        return registerError;    }
    public void setRegisterError(Boolean registerError) {        this.registerError = registerError;    }

    public String doRegister(){
        // Check password confirmation
        if(!password.equals(confirmPassword)){
            passwordError = true;
            return "register";
        }
        else{
            passwordError = false;
        }

        // Try to do register
        if (db.doRegister(username, password)) {
            registerError = false;

            // Tell server that the user registered and logged in
            server.register(username);
            server.login(username);

            // Set this logged user to the UserBean
            user.setUsername(username);
            user.setOnline(true);

            // Set welcome message
            home.setJustRegistered(true);

            return "home?faces-redirect=true";
        }
        else {
            registerError = true;
            return "register";
        }
    }
}