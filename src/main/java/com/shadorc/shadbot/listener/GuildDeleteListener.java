package com.shadorc.shadbot.listener;

import com.shadorc.shadbot.cache.GuildOwnersCache;
import com.shadorc.shadbot.db.DatabaseManager;
import com.shadorc.shadbot.db.guilds.entity.DBGuild;
import com.shadorc.shadbot.music.MusicManager;
import discord4j.core.event.domain.guild.GuildDeleteEvent;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static com.shadorc.shadbot.Shadbot.DEFAULT_LOGGER;

public class GuildDeleteListener implements EventListener<GuildDeleteEvent> {

    @Override
    public Class<GuildDeleteEvent> getEventType() {
        return GuildDeleteEvent.class;
    }

    @Override
    public Mono<Void> execute(GuildDeleteEvent event) {
        DEFAULT_LOGGER.info("{Guild ID: {}} Disconnected", event.getGuildId().asLong());

        return MusicManager.getInstance()
                .destroyConnection(event.getGuildId())
                .then(DatabaseManager.getGuilds()
                        .getDBGuild(event.getGuildId()))
                .flatMap(DBGuild::delete)
                .then(Mono.delay(Duration.ofSeconds(5), Schedulers.boundedElastic()))
                .then(Mono.fromRunnable(() -> GuildOwnersCache.remove(event.getGuildId())));
    }

}
