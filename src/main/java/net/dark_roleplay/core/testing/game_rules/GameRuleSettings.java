package net.dark_roleplay.core.testing.game_rules;

import java.util.HashMap;
import java.util.Map;

import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core_modules.guis.api.components.DRPGuiScreen;
import net.dark_roleplay.library.experimental.variables.wrappers.BooleanWrapper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GameRuleSettings extends DRPGuiScreen{

	public Map<String, BooleanWrapper> booleanRules = new HashMap<String, BooleanWrapper>();

	public GameRuleSettings(World world) {
		GameRules rules = world.getGameRules();
		for(String rule : rules.getRules()) {
			if(rules.areSameType(rule, GameRules.ValueType.BOOLEAN_VALUE)) {
				BooleanWrapper wrapper = new BooleanWrapper(rules.getBoolean(rule));
				this.booleanRules.put(rule, wrapper);
				this.comps.add(new GameRuleComponent(25, this.height += 16, wrapper, rule));
			}
		}
	}

	@Override
    public void onGuiClosed(){
		DRPCorePackets.sendToServer(new ListenerPackets.RemoveListener());
    }
}
