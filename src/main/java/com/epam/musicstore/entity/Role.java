package com.epam.musicstore.entity;

public class Role extends LocaleName {

    private String name;

    public Role(Integer id) {
        setId(id);
        setRoleName(id);
    }
    public Role(){

    }

    private void setRoleName(Integer id){
        RoleName roleName;
        switch (id){
            case 1:
               roleName = RoleName.user;
               break;
            case 2:
                roleName = RoleName.guest;
                break;
            case 3:
                roleName = RoleName.admin;
                break;
            default:
                roleName = RoleName.guest;
        }
        name =  roleName.toString();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + name + '\'' +
                '}';
    }

    public enum RoleName{
        admin,
        user,
        guest
    }
}
