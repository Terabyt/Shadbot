package me.shadorc.discordbot.command.admin;

import java.util.concurrent.ConcurrentHashMap;

import me.shadorc.discordbot.command.AbstractCommand;
import me.shadorc.discordbot.command.CommandCategory;
import me.shadorc.discordbot.command.Context;
import me.shadorc.discordbot.command.Role;
import me.shadorc.discordbot.command.admin.setting.AutoMessageSettingCmd;
import me.shadorc.discordbot.command.admin.setting.ChannelSettingCmd;
import me.shadorc.discordbot.command.admin.setting.NSFWSettingCmd;
import me.shadorc.discordbot.command.admin.setting.PrefixSettingCmd;
import me.shadorc.discordbot.command.admin.setting.SettingCmd;
import me.shadorc.discordbot.command.admin.setting.VolumeSettingCmd;
import me.shadorc.discordbot.data.Storage.Setting;
import me.shadorc.discordbot.utils.BotUtils;
import me.shadorc.discordbot.utils.Utils;
import me.shadorc.discordbot.utils.command.Emoji;
import me.shadorc.discordbot.utils.command.MissingArgumentException;
import sx.blah.discord.util.EmbedBuilder;

public class SettingsManagerCmd extends AbstractCommand {

	private static final ConcurrentHashMap<String, SettingCmd> SUBSETTINGS_MAP = new ConcurrentHashMap<>();

	public SettingsManagerCmd() {
		super(CommandCategory.ADMIN, Role.ADMIN, "settings", "setting", "options", "option");

		SUBSETTINGS_MAP.put(Setting.PREFIX.toString(), new PrefixSettingCmd());
		SUBSETTINGS_MAP.put(Setting.ALLOWED_CHANNELS.toString(), new ChannelSettingCmd());
		SUBSETTINGS_MAP.put(Setting.DEFAULT_VOLUME.toString(), new VolumeSettingCmd());
		SUBSETTINGS_MAP.put(Setting.AUTO_MESSAGE.toString(), new AutoMessageSettingCmd());
		SUBSETTINGS_MAP.put(Setting.NSFW.toString(), new NSFWSettingCmd());
	}

	@Override
	public void execute(Context context) throws MissingArgumentException {
		String[] splitArgs = context.getArg().split(" ", 2);

		String name = splitArgs[0];

		if(name.isEmpty()) {
			throw new MissingArgumentException();
		}

		SettingCmd cmd = SUBSETTINGS_MAP.get(name);
		if(cmd == null) {
			BotUtils.sendMessage(Emoji.MAGNIFYING_GLASS + " This setting does not exist."
					+ " Use `" + context.getPrefix() + "help settings` to see all available settings.", context.getChannel());
			return;
		}

		String arg = splitArgs.length > 1 ? splitArgs[1] : null;

		if("help".equals(arg)) {
			cmd.showHelp(context);
			return;
		}

		try {
			cmd.execute(context, arg);
		} catch (MissingArgumentException e) {
			cmd.showHelp(context);
		}
	}

	@Override
	public void showHelp(Context context) {
		EmbedBuilder builder = Utils.getDefaultEmbed(this)
				.withThumbnail("http://www.emoji.co.uk/files/emoji-one/objects-emoji-one/1898-gear.png")
				.appendDescription("**Change Shadbot's settings for this server.**");

		for(String subCmd : SUBSETTINGS_MAP.keySet()) {
			builder.appendField("Name: " + subCmd, SUBSETTINGS_MAP.get(subCmd).getDescription(), false);
		}

		builder.appendField("Additional help", "`" + context.getPrefix() + "settings <name> help`", false);

		BotUtils.sendMessage(builder.build(), context.getChannel());
	}
}
