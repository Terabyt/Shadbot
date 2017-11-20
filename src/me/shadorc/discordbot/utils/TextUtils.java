package me.shadorc.discordbot.utils;

import me.shadorc.discordbot.data.Config;
import me.shadorc.discordbot.utils.command.Emoji;
import sx.blah.discord.handle.obj.IUser;

public class TextUtils {

	public static final String PLAYLIST_LIMIT_REACHED =
			Emoji.WARNING + " You've reached the maximum number (" + Config.MAX_PLAYLIST_SIZE + ") of tracks in a playlist.";

	public static final String NO_PLAYING_MUSIC =
			Emoji.MUTE + " No currently playing music.";

	private static final String[] SPAM_MESSAGES = { "Take it easy, we are not in a hurry !",
			"Phew.. give me time to rest, you're too fast for me.",
			"I'm not going anywhere, no need to be this fast.",
			"I don't think everyone here want to be spammed by us, just wait a little bit." };

	public static String getSpamMessage() {
		return SPAM_MESSAGES[MathUtils.rand(SPAM_MESSAGES.length)];
	}

	public static String notEnoughCoins(IUser user) {
		return Emoji.BANK + " (**" + user.getName() + "**) You don't have enough coins. You can get some by playing **RPS**, **Hangman** "
				+ "or **Trivia**.";
	}

	public static String noResult(String search) {
		return Emoji.MAGNIFYING_GLASS + " No results for `" + search + "`.";
	}
}
