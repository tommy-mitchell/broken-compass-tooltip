package brokencompasstooltip.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	private ItemStack instance = (ItemStack) (Object) this;

	@Definition(id = "getItem", method = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;")
	@Definition(id = "appendHoverText", method = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V")
	@Expression("this.getItem().appendHoverText(?, ?, ?, ?, ?)")
	@Inject(method = "addDetailsToTooltip", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
	private void onAddDetailsToTooltip(
		final TooltipContext context,
		final TooltipDisplay display,
		final Player player,
		final TooltipFlag tooltipFlag,
		final Consumer<Component> builder,
		CallbackInfo ci
	) {
		instance.addToTooltip(DataComponents.LODESTONE_TRACKER, context, display, builder, tooltipFlag);
	}
}
