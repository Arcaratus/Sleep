package arc.sleep;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class SleepEventFactory
{
    public static boolean canNotSleep(EntityPlayer player)
    {
        PlayerSleepInBedEvent event = new PlayerSleepInBedEvent(player, player.getBedLocation());
        MinecraftForge.EVENT_BUS.post(event);
        return event.isCanceled();
    }

    public static boolean distanceFromSleep(EntityPlayer player, BlockPos bedLocation)
    {
        return Math.abs(player.posX - (double)bedLocation.getX()) > 5.0D || Math.abs(player.posY - (double)bedLocation.getY()) > 3.0D || Math.abs(player.posZ - (double)bedLocation.getZ()) > 5.0D;
    }
}
