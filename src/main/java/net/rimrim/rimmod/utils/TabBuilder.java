package net.rimrim.rimmod.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabBuilder {
    private static final Map<String, CreativeModeTab.Builder> builders = new HashMap<>();
    private static final Map<String, List<Item>> itemsByTab = new HashMap<>();

    // New method to initialize a tab's item list
    public static void initializeTab(String tabId) {
        if (!itemsByTab.containsKey(tabId)) {
            itemsByTab.put(tabId, new ArrayList<>());
        }
    }

    public static CreativeModeTab.Builder startTab(String tabId, CreativeModeTab.Builder builder) {
        builders.put(tabId, builder);
        // Only initialize if not already initialized
        if (!itemsByTab.containsKey(tabId)) {
            itemsByTab.put(tabId, new ArrayList<>());
        }
        return builder;
    }

    public static void addItem(String tabId, Item item) {
        // Initialize if not exists
        if (!itemsByTab.containsKey(tabId)) {
            itemsByTab.put(tabId, new ArrayList<>());
        }
        itemsByTab.get(tabId).add(item);
    }

    public static CreativeModeTab buildTab(String tabId) {
        if (!builders.containsKey(tabId)) {
            throw new IllegalStateException("Tab " + tabId + " not initialized!");
        }

        CreativeModeTab.Builder builder = builders.get(tabId)
                .displayItems((parameters, output) -> {
                    if (itemsByTab.containsKey(tabId)) {
                        for (Item item : itemsByTab.get(tabId)) {
                            output.accept(item);
                        }
                    }
                });

        // Cleanup
        builders.remove(tabId);
        itemsByTab.remove(tabId);

        return builder.build();
    }

    public static void clearTab(String tabId) {
        builders.remove(tabId);
        itemsByTab.remove(tabId);
    }
}
