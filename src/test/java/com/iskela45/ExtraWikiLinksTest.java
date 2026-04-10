package com.iskela45;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExtraWikiLinksTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ExtraWikiLinks.class);
		RuneLite.main(args);
	}
}