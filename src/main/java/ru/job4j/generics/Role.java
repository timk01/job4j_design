package ru.job4j.generics;

public class Role extends Base {

    private Roles role;
    private final String roleHolderInitials;

    public Role(String id, Roles role, String roleHolderInitials) {
        super(id);
        this.role = role;
        this.roleHolderInitials = roleHolderInitials;
    }

    public Roles getRole() {
        return role;
    }

    public String getRoleHolderInitials() {
        return roleHolderInitials;
    }
}
