package brokencompasstooltip.mixin;

import java.util.Optional;
import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.ChatFormatting;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.component.TooltipProvider;

@Mixin(LodestoneTracker.class)
public class LodestoneTrackerMixin implements TooltipProvider {
	@Shadow private Optional<GlobalPos> target;
	@Shadow private boolean tracked;

	@Override
	public void addToTooltip(TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
		var isBroken = this.tracked && this.target.isEmpty();

		if (isBroken) {
			consumer.accept(Component.literal("Broken").withStyle(ChatFormatting.GRAY));
		}
	}
}
