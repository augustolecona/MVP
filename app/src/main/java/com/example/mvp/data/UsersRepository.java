package com.example.mvp.data;

public interface UsersRepository {

    /// Is going to check if we have users on the database, and then show those
    /// if there are no users then load them from the REST API
    /// Once loaded from the API they are cached into the Content Provider
    /// Which in turn saves them into a SQLite database

    void fetchUsers();

    void fetchUser();

    void saveUsers();

    void updateUser();

    void deleteUsers();

}
