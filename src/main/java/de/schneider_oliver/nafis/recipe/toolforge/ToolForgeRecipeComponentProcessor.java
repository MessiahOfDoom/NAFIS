package de.schneider_oliver.nafis.recipe.toolforge;

import java.util.ArrayList;
import java.util.List;

import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class ToolForgeRecipeComponentProcessor implements IComponentProcessor{

	private List<ItemStack> stacks = new ArrayList<ItemStack>();
	private ItemStack out = ItemStack.EMPTY;
	
	@Override
	public void setup(IVariableProvider variables) {
		for(int i = 0; i < 4; i++) {
			stacks.add(variables.get("input_" + i).as(ItemStack.class));
		}
		stacks.removeIf(a -> a.isEmpty());
		out = variables.get("no_nbt_output").as(ItemStack.class);
	}

	@Override
	public IVariable process(String key) {
		if(key.equals("actual_output")) {
			if(!out.isEmpty() && out.getItem() instanceof NafisTool) {
				((NafisTool)out.getItem()).setComponents(out, stacks);
				return IVariable.from(out);
			}
		}
		return null;
	}

}
