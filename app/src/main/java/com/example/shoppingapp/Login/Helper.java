package com.example.shoppingapp.Login;

public class Helper {
    String name;
    String email;
    String phonenumber;
    String password;
    String confirmpassword;
    String Dayofbirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getDayofbirth() {
        return Dayofbirth;
    }

    public void setDayofbirth(String dayofbirth) {
        Dayofbirth = dayofbirth;
    }



    public Helper(String name, String email, String phonenumber, String password, String confirmpassword, String dayofbirth) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.confirmpassword = confirmpassword;
        Dayofbirth = dayofbirth;
    }


}
