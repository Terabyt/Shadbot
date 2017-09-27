package me.shadorc.discordbot.command.fun;

import java.time.temporal.ChronoUnit;

import me.shadorc.discordbot.command.AbstractCommand;
import me.shadorc.discordbot.command.CommandCategory;
import me.shadorc.discordbot.command.Context;
import me.shadorc.discordbot.command.Role;
import me.shadorc.discordbot.utils.BotUtils;
import me.shadorc.discordbot.utils.Utils;
import me.shadorc.discordbot.utils.command.Emoji;
import me.shadorc.discordbot.utils.command.MissingArgumentException;
import me.shadorc.discordbot.utils.command.RateLimiter;
import sx.blah.discord.util.EmbedBuilder;

public class LeetCmd extends AbstractCommand {

	private final RateLimiter rateLimiter;

	public LeetCmd() {
		super(CommandCategory.FUN, Role.USER, "leet");
		this.rateLimiter = new RateLimiter(RateLimiter.COMMON_COOLDOWN, ChronoUnit.SECONDS);
	}

	@Override
	public void execute(Context context) throws MissingArgumentException {
		if(rateLimiter.isSpamming(context)) {
			return;
		}

		if(!context.hasArg()) {
			throw new MissingArgumentException();
		}

		String text = context.getArg()
				.toUpperCase()
				.replace("A", "4")
				.replace("B", "8")
				.replace("E", "3")
				.replace("G", "6")
				.replace("L", "1")
				.replace("O", "0")
				.replace("S", "5")
				.replace("T", "7");

		BotUtils.sendMessage(Emoji.KEYBOARD + " " + text, context.getChannel());
	}

	@Override
	public void showHelp(Context context) {
		EmbedBuilder builder = Utils.getDefaultEmbed(this)
				.appendDescription("**Leetify a text.**")
				.appendField("Usage", "`" + context.getPrefix() + "leet <text>`", false);
		BotUtils.sendMessage(builder.build(), context.getChannel());
	}

}
