package be.lghs.gate.server.repositories;

import be.lghs.gate.server.model.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static be.lghs.gate.server.model.Tables.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    public UsersRecord ensureUserExists(int id, UUID uuid, String name, String username, String email) {
        return dsl.insertInto(USERS)
            .columns(
                USERS.ID,
                USERS.UUID,
                USERS.NAME,
                USERS.USERNAME,
                USERS.EMAIL
            )
            .values(id, uuid, name, username, email)
            .onDuplicateKeyUpdate()
            .set(USERS.NAME, name)
            .set(USERS.USERNAME, username)
            .set(USERS.EMAIL, email)
            .returning(USERS.asterisk())
            .fetchOne();
    }
}
