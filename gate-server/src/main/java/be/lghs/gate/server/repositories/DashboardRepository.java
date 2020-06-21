package be.lghs.gate.server.repositories;

import be.lghs.gate.server.model.Keys;
import be.lghs.gate.server.model.Tables;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final DSLContext dsl;

    public Result<Record4<UUID, String, LocalDateTime, LocalDateTime>> getUserCards(UUID id) {
        return dsl
            .select(
                Tables.CARDS.ID,
                Tables.CARDS.NAME,
                Tables.CARDS.LAST_RENEW,
                Tables.CARDS.LAST_RENEW
                    .plus(Tables.CARDS.LAST_RENEW_DURATION)
                    .as("expiration")
            )
            .from(Tables.USERS)
            .innerJoin(Tables.CARDS).onKey(Keys.CARDS__CARDS_USER_ID_FKEY)
            .fetch();
    }
}
