package com.example.university.forms;

import javax.validation.constraints.NotEmpty;

public class PasswordForm {

    @NotEmpty(message = "Old Password is required.")
    private String oldPassword;
    @NotEmpty(message = "New Password is required.")
    private String newPassword;
    @NotEmpty(message = "Confirm Password is required.")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
