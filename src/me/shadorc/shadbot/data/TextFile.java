package me.shadorc.shadbot.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.shadorc.shadbot.utils.Utils;
import me.shadorc.shadbot.utils.embed.log.LogUtils;

public class TextFile {

	private final List<String> texts;

	public TextFile(String path) {
		this.texts = new ArrayList<>();

		final File file = new File(path);
		if(file.exists()) {
			try {
				this.texts.addAll(Arrays.asList(Utils.read(file).split("\n")));
			} catch (IOException err) {
				LogUtils.error(err, String.format("An error occurred while reading text file: %s", file.getPath()));
			}
		}
	}

	public List<String> getTexts() {
		return this.texts;
	}

	public String getText() {
		return Utils.randValue(this.getTexts());
	}

}
