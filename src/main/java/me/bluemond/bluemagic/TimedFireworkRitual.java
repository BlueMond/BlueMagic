package me.bluemond.bluemagic;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;


public class TimedFireworkRitual {

    private BlockPos ritualCenter;
    private ServerWorld world;
    private long lastTick;
    private final int modifier;
    private NonNullList<ItemStack> reagents;
    private List<ItemStack> fireworkStacks;

    public TimedFireworkRitual(ServerWorld world, BlockPos ritualCenter, NonNullList<ItemStack> reagents){
        this.world = world;
        this.ritualCenter = ritualCenter;
        lastTick = world.getGameTime();
        this.reagents = reagents;
        modifier = 5;

        fireworkStacks = new ArrayList<>();
        fillStacks();
    }

    private void fillStacks(){
        for(ItemStack fireworkStack : reagents){
            for(int i = 0; i < modifier; i++){
                fireworkStacks.add(fireworkStack);
            }
        }
    }

    public boolean hasNextFireworkStack(){
        return !fireworkStacks.isEmpty();
    }

    public ItemStack getNextFireworkStack(){
        if(hasNextFireworkStack()){
            ItemStack fireworkStack = fireworkStacks.get(0);
            fireworkStacks.remove(0);
            return fireworkStack;
        }

        return null;
    }

    public long getLastTick() {
        return lastTick;
    }

    public void setLastTick(long lastTick) {
        this.lastTick = lastTick;
    }


    public BlockPos getRitualCenter() {
        return ritualCenter;
    }

    public ServerWorld getWorld() {
        return world;
    }
}
