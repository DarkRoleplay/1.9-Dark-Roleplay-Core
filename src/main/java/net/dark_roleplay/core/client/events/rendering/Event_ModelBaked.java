package net.dark_roleplay.core.client.events.rendering;

import net.dark_roleplay.core.client.items.models.SmithableSword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_ModelBaked {

	public static final Event_ModelBaked instance = new Event_ModelBaked();

	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event) {
		// Find the existing mapping for ChessboardModel - we added it in
		// StartupClientOnly.initClientOnly(), which
		// caused it to be loaded from resources
		// (model/items/mbe15_item_chessboard.json) just like an ordinary item
		// Replace the mapping with our ISmartBlockModel, using the existing
		// mapped model as the base for the smart model.
		
		Object object = event.getModelRegistry().getObject(SmithableSword.modelResourceLocation);
		if (object instanceof IBakedModel) {
			IBakedModel existingModel = (IBakedModel) object;
			SmithableSword customModel = new SmithableSword(existingModel);
			event.getModelRegistry().putObject(SmithableSword.modelResourceLocation, customModel);
		}
	}

}
