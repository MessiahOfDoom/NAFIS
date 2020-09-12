package de.schneider_oliver.nafis.api;

import de.schneider_oliver.nafis.item.item.ToolComponent;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;

public interface ToolPartFactory {
	public ToolComponent create(ToolMaterial mat, Identifier matName);
}
