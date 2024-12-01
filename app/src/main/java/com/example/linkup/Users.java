package com.example.linkup;

public class Users {
    String fname, lname, mail, pwd, id, status, profilepic;

    public Users() {
        // Required for calls to DataSnapshot.getValue(Users.class)
    }

//    public Users(String id, String fname,String lname, String mail, String pwd, String profilepic, String status){
//        this.id = id;
//        this.fname = fname;
//        this.lname = lname;
//        this.mail = mail;
//        this.pwd = pwd;
//        this.profilepic = profilepic;
//        this.status = status;
//    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pass) {
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public Users(String fname, String lname, String mail, String pwd, String id, String status, String profilepic){
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
        this.pwd = pwd;
        this.id = id;
        this.status = status;
        this.profilepic = profilepic;
    }
}
