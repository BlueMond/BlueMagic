package me.bluemond.bluemagic.ritualeffects;

import com.ma.api.recipes.IRitualRecipe;
import com.ma.api.rituals.RitualEffect;
import me.bluemond.bluemagic.TimedFireworkRitual;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RitualEffectFirework extends RitualEffect{

	private List<TimedFireworkRitual> timedFireworkRituals;

	public RitualEffectFirework(ResourceLocation ritualName) {
		super(ritualName);
		timedFireworkRituals = new ArrayList<>();
	}


	@Override
	protected boolean applyRitualEffect(PlayerEntity caster, ServerWorld world, BlockPos ritualCenter, IRitualRecipe recipe, NonNullList<ItemStack> reagents) {

		reagents.removeIf(p -> p.getItem() == Items.AIR || p.getItem() == Items.REPEATER);

		TimedFireworkRitual timedFireworkRitual = new TimedFireworkRitual(world, ritualCenter, reagents);
		timedFireworkRituals.add(timedFireworkRitual);
		
		return true;
	}

	@Override
	protected int getApplicationTicks(ServerWorld arg0, BlockPos arg1, IRitualRecipe arg2, NonNullList<ItemStack> arg3) {
		return 400;
	}

	public void performWorldTick(World world){
		Iterator<TimedFireworkRitual> timedRitualIterator = timedFireworkRituals.iterator();

		while(timedRitualIterator.hasNext()){
			TimedFireworkRitual timedFireworkRitual = timedRitualIterator.next();

			if(timedFireworkRitual.hasNextFireworkStack()){

				if(timedFireworkRitual.getWorld().getDimensionKey().equals(world.getDimensionKey())) {
					long curTick = world.getGameTime();
					long lastTick = timedFireworkRitual.getLastTick();

					if (curTick - lastTick >= 20) {
						timedFireworkRitual.setLastTick(curTick);
						throwFirework(
								timedFireworkRitual.getWorld(), timedFireworkRitual.getRitualCenter(), timedFireworkRitual.getNextFireworkStack());
					}
				}
			}else{
				timedRitualIterator.remove();
			}
		}
	}

	private void throwFirework(ServerWorld world, BlockPos ritualCenter, ItemStack fireworkStack){
		FireworkRocketEntity firework = new FireworkRocketEntity(
				world, ritualCenter.getX(), ritualCenter.up().getY(), ritualCenter.getZ(), fireworkStack);
		world.addEntity(firework);
	}

}
