package ru.job4j.generics;

public class RoleStore implements Store<Role> {

    private final Store<Role> roleStore = new MemStore<>();

    @Override
    public void add(Role role) {
        roleStore.add(role);
    }

    @Override
    public boolean replace(String id, Role role) {
        return roleStore.replace(id, role);
    }

    @Override
    public void delete(String id) {
        roleStore.delete(id);
    }

    @Override
    public Role findById(String id) {
        return roleStore.findById(id);
    }
}