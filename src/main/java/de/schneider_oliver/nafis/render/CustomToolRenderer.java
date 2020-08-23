package de.schneider_oliver.nafis.render;

import java.util.ArrayList;

import de.schneider_oliver.nafis.item.item.NafisTool;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class CustomToolRenderer implements BuiltinItemRenderer{

	@Override
	public void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		if(!stack.isEmpty() && stack.getItem() instanceof NafisTool) {
			ArrayList<ItemStack> stacks = NafisTool.getComponents(stack);
			ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
			matrices.translate(0.5, 0.5, 0.5);
			for(ItemStack s : stacks) {
				renderer.renderItem(s, ModelTransformation.Mode.NONE, light, overlay, matrices, vertexConsumers);
			}
		}
		matrices.pop();
	}

}
