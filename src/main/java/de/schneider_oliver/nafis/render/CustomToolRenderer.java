/*******************************************************************************
 * Copyright (c) 2020 Oliver Schneider
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU General Public License version 3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
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
import net.minecraft.item.Items;

public class CustomToolRenderer implements BuiltinItemRenderer{

	private static CustomToolRenderer INSTANCE;
	
	public static CustomToolRenderer getInstance() {
		return INSTANCE == null ? setInstance(new CustomToolRenderer()) : INSTANCE;
	}
	
	private static CustomToolRenderer setInstance(CustomToolRenderer r) {
		INSTANCE = r;
		return INSTANCE;
	}
	
	@Override
	public void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		if(!stack.isEmpty() && stack.getItem() instanceof NafisTool) {
			ArrayList<ItemStack> stacks = ((NafisTool)stack.getItem()).getComponents(stack);
			ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
			matrices.translate(0.5, 0.5, 0.5);
			for(ItemStack s : stacks) {
				if(s.getItem().equals(Items.BARRIER)) {
					matrices.push();
					matrices.scale(1F, 1F, 0.4F);
					renderer.renderItem(s, ModelTransformation.Mode.NONE, light, overlay, matrices, vertexConsumers);
					matrices.pop();
					continue;
				}
				renderer.renderItem(s, ModelTransformation.Mode.NONE, light, overlay, matrices, vertexConsumers);
			}
		}
		matrices.pop();
	}

}
