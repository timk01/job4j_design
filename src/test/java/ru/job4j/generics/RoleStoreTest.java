package ru.job4j.generics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleStoreTest {

    @Test
    void whenAddAndFindThenRoleIsBuyerAndInitialIsPopov() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.BUYER.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Popov V.V.");
        });
    }

    @Test
    void whenCanNotFindTheExistingRoleByWrongId() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        Role role = roleStore.findById("10");
        assertThat(role).isNull();
    }

    @Test
    void whenAddingRolesWithSameIdLeadsToOnlyFirstAdded() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.add(new Role("1", Roles.BUYER, "Ivanov I.I."));
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.BUYER.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Popov V.V.");
        });
    }

    @Test
    void whenRoleReplacementNameIsCorrect() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.replace("1", new Role("1", Roles.BUYER, "Ivanov I.I."));
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.BUYER.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Ivanov I.I.");
        });
    }

    @Test
    void whenRoleReplacementRoleFieldIsCorrect() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.replace("1", new Role("1", Roles.ADMINISTRATOR, "Popov V.V."));
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.ADMINISTRATOR.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Popov V.V.");
        });
    }

    @Test
    void whenAllRoleReplacementFieldsAreCorrect() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.replace("1", new Role("1", Roles.ADMINISTRATOR, "Ivanov I.I."));
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.ADMINISTRATOR.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Ivanov I.I.");
        });
    }

    @Test
    void whenCanNotReplaceUserViaWrongId() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.replace("10", new Role("1", Roles.ADMINISTRATOR, "Ivanov I.I."));
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.BUYER.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Popov V.V.");
        });
    }

    @Test
    void whenUserDeletionIsOk() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.delete("1");
        Role result = roleStore.findById("1");
        assertThat(result).isNull();
    }

    @Test
    void whenCanNotDeleteUserViaWrongId() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        roleStore.delete("10");
        Role role = roleStore.findById("1");
        assertThat((role)).satisfies(holder -> {
            assertThat(holder.getRole().getDescription()).isEqualTo(Roles.BUYER.getDescription());
            assertThat(holder.getRoleHolderInitials()).isEqualTo("Popov V.V.");
        });
    }

    @Test
    void whenReplaceOkThenTrue() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        boolean result = roleStore.replace("1", new Role("1", Roles.ADMINISTRATOR, "Ivanov I.I."));
        assertThat(result).isTrue();
    }

    @Test
    void whenReplaceNotOkThenFalse() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", Roles.BUYER, "Popov V.V."));
        boolean result = roleStore.replace("10", new Role("1", Roles.ADMINISTRATOR, "Ivanov I.I."));
        assertThat(result).isFalse();
    }
}