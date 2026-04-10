package com.iskela45;

import com.google.inject.Provides;
import java.util.Map;
import java.util.stream.Stream;
import javax.inject.Inject;
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

@PluginDescriptor(
	name = "Extra Wiki Links"
)
public class ExtraWikiLinks extends Plugin
{
	private static final String WIKI_BASE = "https://oldschool.runescape.wiki/w/";

	// Attack, Strength, and Defence all use the melee training guides.
	// Hitpoints is omitted — no training guide exists.
	private static final Map<String, String> MEMBERS_GUIDES = Map.ofEntries(
		Map.entry("Attack", "Pay-to-play_melee_training"),
		Map.entry("Strength", "Pay-to-play_melee_training"),
		Map.entry("Defence", "Pay-to-play_melee_training"),
		Map.entry("Ranged", "Pay-to-play_Ranged_training"),
		Map.entry("Prayer", "Pay-to-play_Prayer_training"),
		Map.entry("Magic", "Pay-to-play_Magic_training"),
		Map.entry("Cooking", "Pay-to-play_Cooking_training"),
		Map.entry("Woodcutting", "Pay-to-play_Woodcutting_training"),
		Map.entry("Fletching", "Fletching_training"),
		Map.entry("Fishing", "Pay-to-play_Fishing_training"),
		Map.entry("Firemaking", "Pay-to-play_Firemaking_training"),
		Map.entry("Crafting", "Pay-to-play_Crafting_training"),
		Map.entry("Smithing", "Pay-to-play_Smithing_training"),
		Map.entry("Mining", "Pay-to-play_Mining_training"),
		Map.entry("Herblore", "Herblore_training"),
		Map.entry("Agility", "Agility_training"),
		Map.entry("Thieving", "Thieving_training"),
		Map.entry("Slayer", "Slayer_training"),
		Map.entry("Farming", "Farming_training"),
		Map.entry("Runecraft", "Pay-to-play_Runecraft_training"),
		Map.entry("Hunter", "Hunter_training"),
		Map.entry("Construction", "Construction_training"),
		Map.entry("Sailing", "Sailing_training")
	);

	// Members-only skills have no F2P guide
	private static final Map<String, String> F2P_GUIDES = Map.ofEntries(
		Map.entry("Attack", "Free-to-play_melee_training"),
		Map.entry("Strength", "Free-to-play_melee_training"),
		Map.entry("Defence", "Free-to-play_melee_training"),
		Map.entry("Ranged", "Free-to-play_Ranged_training"),
		Map.entry("Prayer", "Free-to-play_Prayer_training"),
		Map.entry("Magic", "Free-to-play_Magic_training"),
		Map.entry("Cooking", "Free-to-play_Cooking_training"),
		Map.entry("Woodcutting", "Free-to-play_Woodcutting_training"),
		Map.entry("Fishing", "Free-to-play_Fishing_training"),
		Map.entry("Firemaking", "Free-to-play_Firemaking_training"),
		Map.entry("Crafting", "Free-to-play_Crafting_training"),
		Map.entry("Smithing", "Free-to-play_Smithing_training"),
		Map.entry("Mining", "Free-to-play_Mining_training"),
		Map.entry("Runecraft", "Free-to-play_Runecraft_training")
	);

	private static final Map<String, String> IRONMAN_GUIDES = Map.ofEntries(
		Map.entry("Attack", "Ironman_Guide/Melee"),
		Map.entry("Strength", "Ironman_Guide/Melee"),
		Map.entry("Defence", "Ironman_Guide/Melee"),
		Map.entry("Ranged", "Ironman_Guide/Ranged"),
		Map.entry("Prayer", "Ironman_Guide/Prayer"),
		Map.entry("Magic", "Ironman_Guide/Magic"),
		Map.entry("Cooking", "Ironman_Guide/Cooking"),
		Map.entry("Woodcutting", "Ironman_Guide/Woodcutting"),
		Map.entry("Fletching", "Ironman_Guide/Fletching"),
		Map.entry("Fishing", "Ironman_Guide/Fishing"),
		Map.entry("Firemaking", "Ironman_Guide/Firemaking"),
		Map.entry("Crafting", "Ironman_Guide/Crafting"),
		Map.entry("Smithing", "Ironman_Guide/Smithing"),
		Map.entry("Mining", "Ironman_Guide/Mining"),
		Map.entry("Herblore", "Ironman_Guide/Herblore"),
		Map.entry("Agility", "Ironman_Guide/Agility"),
		Map.entry("Thieving", "Ironman_Guide/Thieving"),
		Map.entry("Slayer", "Ironman_Guide/Slayer"),
		Map.entry("Farming", "Ironman_Guide/Farming"),
		Map.entry("Runecraft", "Ironman_Guide/Runecraft"),
		Map.entry("Hunter", "Ironman_Guide/Hunter"),
		Map.entry("Construction", "Ironman_Guide/Construction"),
		Map.entry("Sailing", "Ironman_Guide/Sailing")
	);

	private static final Map<String, String> UIM_GUIDES = Map.ofEntries(
		Map.entry("Attack", "Ultimate_Ironman_Guide/Melee"),
		Map.entry("Strength", "Ultimate_Ironman_Guide/Melee"),
		Map.entry("Defence", "Ultimate_Ironman_Guide/Melee"),
		Map.entry("Ranged", "Ultimate_Ironman_Guide/Ranged"),
		Map.entry("Prayer", "Ultimate_Ironman_Guide/Prayer"),
		Map.entry("Magic", "Ultimate_Ironman_Guide/Magic"),
		Map.entry("Cooking", "Ultimate_Ironman_Guide/Cooking"),
		Map.entry("Woodcutting", "Ultimate_Ironman_Guide/Woodcutting"),
		Map.entry("Fletching", "Ultimate_Ironman_Guide/Fletching"),
		Map.entry("Fishing", "Ultimate_Ironman_Guide/Fishing"),
		Map.entry("Firemaking", "Ultimate_Ironman_Guide/Firemaking"),
		Map.entry("Crafting", "Ultimate_Ironman_Guide/Crafting"),
		Map.entry("Smithing", "Ultimate_Ironman_Guide/Smithing"),
		Map.entry("Mining", "Ultimate_Ironman_Guide/Mining"),
		Map.entry("Herblore", "Ultimate_Ironman_Guide/Herblore"),
		Map.entry("Agility", "Ultimate_Ironman_Guide/Agility"),
		Map.entry("Thieving", "Ultimate_Ironman_Guide/Thieving"),
		Map.entry("Slayer", "Ultimate_Ironman_Guide/Slayer"),
		Map.entry("Farming", "Ultimate_Ironman_Guide/Farming"),
		Map.entry("Runecraft", "Ultimate_Ironman_Guide/Runecraft"),
		Map.entry("Hunter", "Ultimate_Ironman_Guide/Hunter"),
		Map.entry("Construction", "Ultimate_Ironman_Guide/Construction"),
		Map.entry("Sailing", "Ultimate_Ironman_Guide/Sailing")
	);

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
		if (widget == null || widget.getActions() == null || widget.getParentId() != InterfaceID.Stats.UNIVERSE)
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

		String skillName = action.replaceFirst("^View ", "").replaceFirst(" guide$", "");
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
		client.getMenu().createMenuEntry(-1)
			.setOption(option)
			.setTarget(target)
			.setType(MenuAction.RUNELITE)
			.onClick(e -> LinkBrowser.browse(url));
	}

	private Widget getWidget(int wid, int index)
	{
		Widget w = client.getWidget(wid);
		if (w != null && index != -1)
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