package com.iskela45;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("extrawikilinks")
public interface ExtraWikiLinksConfig extends Config
{
	@ConfigItem(
		keyName = "groupEntries",
		name = "Group as submenu",
		description = "Group all enabled wiki link options under a single right-click entry",
		position = 0
	)
	default boolean groupEntries()
	{
		return true;
	}

	@ConfigSection(
		name = "Links",
		description = "Choose which wiki links to show when right-clicking a skill",
		position = 1
	)
	String links = "links";

	@ConfigItem(
		keyName = "levelUpTable",
		name = "Level-up table",
		description = "Show a link to the skill's level-up table on the wiki",
		section = links,
		position = 1
	)
	default boolean levelUpTable()
	{
		return true;
	}

	@ConfigItem(
		keyName = "temporaryBoosts",
		name = "Temporary boosts",
		description = "Show a link to the skill's temporary boosts section on the wiki",
		section = links,
		position = 2
	)
	default boolean temporaryBoosts()
	{
		return true;
	}

	@ConfigItem(
		keyName = "quests",
		name = "Quests",
		description = "Show a link to the skill's quests section on the wiki",
		section = links,
		position = 3
	)
	default boolean quests()
	{
		return false;
	}

	@ConfigItem(
		keyName = "membersGuide",
		name = "Members training guide",
		description = "Show a link to the skill's members training guide on the wiki",
		section = links,
		position = 4
	)
	default boolean membersGuide()
	{
		return false;
	}

	@ConfigItem(
		keyName = "f2pGuide",
		name = "Free-to-play training guide",
		description = "Show a link to the skill's free-to-play training guide on the wiki",
		section = links,
		position = 5
	)
	default boolean f2pGuide()
	{
		return false;
	}

	@ConfigItem(
		keyName = "ironmanGuide",
		name = "Ironman training guide",
		description = "Show a link to the skill's ironman training guide on the wiki",
		section = links,
		position = 6
	)
	default boolean ironmanGuide()
	{
		return false;
	}

	@ConfigItem(
		keyName = "uimGuide",
		name = "Ultimate Ironman training guide",
		description = "Show a link to the skill's ultimate ironman training guide on the wiki",
		section = links,
		position = 7
	)
	default boolean uimGuide()
	{
		return false;
	}
}
