package me.shadorc.shadbot.command.music;

import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;
import me.shadorc.shadbot.core.command.AbstractCommand;
import me.shadorc.shadbot.core.command.CommandCategory;
import me.shadorc.shadbot.core.command.Context;
import me.shadorc.shadbot.core.command.annotation.Command;
import me.shadorc.shadbot.core.command.annotation.RateLimited;
import me.shadorc.shadbot.music.GuildMusic;
import me.shadorc.shadbot.utils.DiscordUtils;
import me.shadorc.shadbot.utils.embed.help.HelpBuilder;
import me.shadorc.shadbot.utils.object.Emoji;
import reactor.core.publisher.Mono;

@RateLimited
@Command(category = CommandCategory.MUSIC, names = { "shuffle" })
public class ShuffleCmd extends AbstractCommand {

	@Override
	public Mono<Void> execute(Context context) {
		final GuildMusic guildMusic = context.requireGuildMusic();

		return DiscordUtils.requireSameVoiceChannel(context)
				.map(voiceChannelId -> {
					guildMusic.getTrackScheduler().shufflePlaylist();
					return String.format(Emoji.CHECK_MARK + " Playlist shuffled by **%s**.", context.getUsername());
				})
				.flatMap(message -> context.getChannel()
						.flatMap(channel -> DiscordUtils.sendMessage(message, channel)))
				.then();
	}

	@Override
	public Mono<Consumer<? super EmbedCreateSpec>> getHelp(Context context) {
		return new HelpBuilder(this, context)
				.setDescription("Shuffle current playlist.")
				.build();
	}

}
