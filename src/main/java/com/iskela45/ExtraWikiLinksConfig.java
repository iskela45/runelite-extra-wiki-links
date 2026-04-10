package com.iskela45;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("extrawikilinks")
public interface ExtraWikiLinksConfig extends Config
{
	@ConfigItem(
		keyName = "levelUpTable",
		name = "Level-up table",
		description = "Show a link to the skill's level-up table on the wiki when right-clicking a skill",
		position = 1
	)
	default boolean levelUpTable()
	{
		return true;
	}

	@ConfigItem(
		keyName = "membersGuide",
		name = "Members training guide",
		description = "Show a link to the skill's members training guide on the wiki",
		position = 2
	)
	default boolean membersGuide()
	{
		return false;
	}

	@ConfigItem(
		keyName = "f2pGuide",
		name = "Free-to-play training guide",
		description = "Show a link to the skill's free-to-play training guide on the wiki",
		position = 3
	)
	default boolean f2pGuide()
	{
		return false;
	}

	@ConfigItem(
		keyName = "ironmanGuide",
		name = "Ironman training guide",
		description = "Show a link to the skill's ironman training guide on the wiki",
		position = 4
	)
	default boolean ironmanGuide()
	{
		return false;
	}

	@ConfigItem(
		keyName = "uimGuide",
		name = "Ultimate Ironman training guide",
		description = "Show a link to the skill's ultimate ironman training guide on the wiki",
		position = 5
	)
	default boolean uimGuide()
	{
		return false;
	}
}
