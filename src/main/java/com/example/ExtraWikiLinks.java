package com.example;

import com.google.inject.Provides;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.LinkBrowser;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Extra Wiki Links"
)
public class ExtraWikiLinks extends Plugin
{
	private static final String WIKI_BASE = "https://oldschool.runescape.wiki/w/";

	// Attack, Strength, and Defence all use the melee training guides.
	// Runecrafting maps to "Runecraft" in wiki URLs.
	// Hitpoints is omitted — no training guide exists.
	private static final Map<String, String> MEMBERS_GUIDES = new HashMap<>();
	private static final Map<String, String> F2P_GUIDES = new HashMap<>();
	private static final Map<String, String> IRONMAN_GUIDES = new HashMap<>();
	private static final Map<String, String> UIM_GUIDES = new HashMap<>();

	static
	{
		MEMBERS_GUIDES.put("Attack", "Pay-to-play_melee_training");
		MEMBERS_GUIDES.put("Strength", "Pay-to-play_melee_training");
		MEMBERS_GUIDES.put("Defence", "Pay-to-play_melee_training");
		MEMBERS_GUIDES.put("Ranged", "Pay-to-play_Ranged_training");
		MEMBERS_GUIDES.put("Prayer", "Pay-to-play_Prayer_training");
		MEMBERS_GUIDES.put("Magic", "Pay-to-play_Magic_training");
		MEMBERS_GUIDES.put("Cooking", "Pay-to-play_Cooking_training");
		MEMBERS_GUIDES.put("Woodcutting", "Pay-to-play_Woodcutting_training");
		MEMBERS_GUIDES.put("Fletching", "Fletching_training");
		MEMBERS_GUIDES.put("Fishing", "Pay-to-play_Fishing_training");
		MEMBERS_GUIDES.put("Firemaking", "Pay-to-play_Firemaking_training");
		MEMBERS_GUIDES.put("Crafting", "Pay-to-play_Crafting_training");
		MEMBERS_GUIDES.put("Smithing", "Pay-to-play_Smithing_training");
		MEMBERS_GUIDES.put("Mining", "Pay-to-play_Mining_training");
		MEMBERS_GUIDES.put("Herblore", "Herblore_training");
		MEMBERS_GUIDES.put("Agility", "Agility_training");
		MEMBERS_GUIDES.put("Thieving", "Thieving_training");
		MEMBERS_GUIDES.put("Slayer", "Slayer_training");
		MEMBERS_GUIDES.put("Farming", "Farming_training");
		MEMBERS_GUIDES.put("Runecrafting", "Pay-to-play_Runecraft_training");
		MEMBERS_GUIDES.put("Hunter", "Hunter_training");
		MEMBERS_GUIDES.put("Construction", "Construction_training");
		MEMBERS_GUIDES.put("Sailing", "Sailing_training");

		F2P_GUIDES.put("Attack", "Free-to-play_melee_training");
		F2P_GUIDES.put("Strength", "Free-to-play_melee_training");
		F2P_GUIDES.put("Defence", "Free-to-play_melee_training");
		F2P_GUIDES.put("Ranged", "Free-to-play_Ranged_training");
		F2P_GUIDES.put("Prayer", "Free-to-play_Prayer_training");
		F2P_GUIDES.put("Magic", "Free-to-play_Magic_training");
		F2P_GUIDES.put("Cooking", "Free-to-play_Cooking_training");
		F2P_GUIDES.put("Woodcutting", "Free-to-play_Woodcutting_training");
		F2P_GUIDES.put("Fishing", "Free-to-play_Fishing_training");
		F2P_GUIDES.put("Firemaking", "Free-to-play_Firemaking_training");
		F2P_GUIDES.put("Crafting", "Free-to-play_Crafting_training");
		F2P_GUIDES.put("Smithing", "Free-to-play_Smithing_training");
		F2P_GUIDES.put("Mining", "Free-to-play_Mining_training");
		F2P_GUIDES.put("Runecrafting", "Free-to-play_Runecraft_training");
		// Members-only skills have no F2P guide

		IRONMAN_GUIDES.put("Attack", "Ironman_Guide/Melee");
		IRONMAN_GUIDES.put("Strength", "Ironman_Guide/Melee");
		IRONMAN_GUIDES.put("Defence", "Ironman_Guide/Melee");
		IRONMAN_GUIDES.put("Ranged", "Ironman_Guide/Ranged");
		IRONMAN_GUIDES.put("Prayer", "Ironman_Guide/Prayer");
		IRONMAN_GUIDES.put("Magic", "Ironman_Guide/Magic");
		IRONMAN_GUIDES.put("Cooking", "Ironman_Guide/Cooking");
		IRONMAN_GUIDES.put("Woodcutting", "Ironman_Guide/Woodcutting");
		IRONMAN_GUIDES.put("Fletching", "Ironman_Guide/Fletching");
		IRONMAN_GUIDES.put("Fishing", "Ironman_Guide/Fishing");
		IRONMAN_GUIDES.put("Firemaking", "Ironman_Guide/Firemaking");
		IRONMAN_GUIDES.put("Crafting", "Ironman_Guide/Crafting");
		IRONMAN_GUIDES.put("Smithing", "Ironman_Guide/Smithing");
		IRONMAN_GUIDES.put("Mining", "Ironman_Guide/Mining");
		IRONMAN_GUIDES.put("Herblore", "Ironman_Guide/Herblore");
		IRONMAN_GUIDES.put("Agility", "Ironman_Guide/Agility");
		IRONMAN_GUIDES.put("Thieving", "Ironman_Guide/Thieving");
		IRONMAN_GUIDES.put("Slayer", "Ironman_Guide/Slayer");
		IRONMAN_GUIDES.put("Farming", "Ironman_Guide/Farming");
		IRONMAN_GUIDES.put("Runecrafting", "Ironman_Guide/Runecraft");
		IRONMAN_GUIDES.put("Hunter", "Ironman_Guide/Hunter");
		IRONMAN_GUIDES.put("Construction", "Ironman_Guide/Construction");
		IRONMAN_GUIDES.put("Sailing", "Ironman_Guide/Sailing");

		UIM_GUIDES.put("Attack", "Ultimate_Ironman_Guide/Melee");
		UIM_GUIDES.put("Strength", "Ultimate_Ironman_Guide/Melee");
		UIM_GUIDES.put("Defence", "Ultimate_Ironman_Guide/Melee");
		UIM_GUIDES.put("Ranged", "Ultimate_Ironman_Guide/Ranged");
		UIM_GUIDES.put("Prayer", "Ultimate_Ironman_Guide/Prayer");
		UIM_GUIDES.put("Magic", "Ultimate_Ironman_Guide/Magic");
		UIM_GUIDES.put("Cooking", "Ultimate_Ironman_Guide/Cooking");
		UIM_GUIDES.put("Woodcutting", "Ultimate_Ironman_Guide/Woodcutting");
		UIM_GUIDES.put("Fletching", "Ultimate_Ironman_Guide/Fletching");
		UIM_GUIDES.put("Fishing", "Ultimate_Ironman_Guide/Fishing");
		UIM_GUIDES.put("Firemaking", "Ultimate_Ironman_Guide/Firemaking");
		UIM_GUIDES.put("Crafting", "Ultimate_Ironman_Guide/Crafting");
		UIM_GUIDES.put("Smithing", "Ultimate_Ironman_Guide/Smithing");
		UIM_GUIDES.put("Mining", "Ultimate_Ironman_Guide/Mining");
		UIM_GUIDES.put("Herblore", "Ultimate_Ironman_Guide/Herblore");
		UIM_GUIDES.put("Agility", "Ultimate_Ironman_Guide/Agility");
		UIM_GUIDES.put("Thieving", "Ultimate_Ironman_Guide/Thieving");
		UIM_GUIDES.put("Slayer", "Ultimate_Ironman_Guide/Slayer");
		UIM_GUIDES.put("Farming", "Ultimate_Ironman_Guide/Farming");
		UIM_GUIDES.put("Runecrafting", "Ultimate_Ironman_Guide/Runecraft");
		UIM_GUIDES.put("Hunter", "Ultimate_Ironman_Guide/Hunter");
		UIM_GUIDES.put("Construction", "Ultimate_Ironman_Guide/Construction");
		UIM_GUIDES.put("Sailing", "Ultimate_Ironman_Guide/Sailing");
	}

	@Inject
	private Client client;

	@Inject
	private ExtraWikiLinksConfig config;

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		int widgetIndex = event.getActionParam0();
		int widgetId = event.getActionParam1();

		if (event.getType() != MenuAction.CC_OP.getId() || WidgetUtil.componentToInterface(widgetId) != InterfaceID.STATS)
		{
			return;
		}

		Widget widget = getWidget(widgetId, widgetIndex);
		if (widget.getActions() == null || widget.getParentId() != InterfaceID.Stats.UNIVERSE)
		{
			return;
		}

		String action = Stream.of(widget.getActions())
			.filter(s -> s != null && !s.isEmpty())
			.findFirst()
			.orElse(null);
		if (action == null)
		{
			return;
		}

		String skillName = action.replace("View ", "").replace(" guide", "");
		String cleanSkillName = Text.removeTags(skillName);

		if (config.uimGuide()) addGuideEntry("UIM guide", UIM_GUIDES, cleanSkillName, skillName);
		if (config.ironmanGuide()) addGuideEntry("Ironman guide", IRONMAN_GUIDES, cleanSkillName, skillName);
		if (config.f2pGuide()) addGuideEntry("F2P guide", F2P_GUIDES, cleanSkillName, skillName);
		if (config.membersGuide()) addGuideEntry("Members guide", MEMBERS_GUIDES, cleanSkillName, skillName);
		if (config.levelUpTable()) addEntry("Level-up table", cleanSkillName + "/Level_up_table", skillName);
	}

	private void addGuideEntry(String option, Map<String, String> guides, String cleanSkillName, String target)
	{
		String urlPath = guides.get(cleanSkillName);
		if (urlPath == null)
		{
			return;
		}
		addEntry(option, urlPath, target);
	}

	private void addEntry(String option, String urlPath, String target)
	{
		final String url = WIKI_BASE + urlPath;
		client.createMenuEntry(-1)
			.setOption(option)
			.setTarget(target)
			.setType(MenuAction.RUNELITE)
			.onClick(e -> LinkBrowser.browse(url));
	}

	private Widget getWidget(int wid, int index)
	{
		Widget w = client.getWidget(wid);
		if (index != -1)
		{
			w = w.getChild(index);
		}
		return w;
	}

	@Provides
	ExtraWikiLinksConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExtraWikiLinksConfig.class);
	}
}