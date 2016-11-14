package arc.sleep;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod(modid = Sleep.MODID, version = Sleep.VERSION, name = "Sleep", guiFactory = "arc.sleep.ConfigGuiFactory")
public class Sleep
{
    public final static String MODID = "Sleep";
    public final static String VERSION = "@VERSION@";

    private static Configuration config;

    public static String configCategory = "Sleeping Configs";

    public static boolean regen;
    public static boolean hunger;
    public static int regenSeconds;
    public static int regenLevel;
    public static int hungerSeconds;
    public static int hungerLevel;

    public static boolean alwaysForceSetSpawn;

    public static Configuration getConfig()
    {
        return config;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        configInit(new File(event.getModConfigurationDirectory(), "Sleep.cfg"));
        MinecraftForge.EVENT_BUS.register(new SleepEvent());
    }

    public class SleepEvent
    {
        @SubscribeEvent
        public void wakeUpEvent(PlayerWakeUpEvent event)
        {
            if (event.getEntityPlayer() != null)
            {
                if (event.getEntityPlayer().getHealth() < event.getEntityPlayer().getMaxHealth())
                {
                    if (regen)
                    {
                        event.getEntityPlayer().addPotionEffect(new PotionEffect(MobEffects.regeneration, regenSeconds * 20, regenLevel - 1));
                    }
                    else
                    {
                        event.getEntityPlayer().setHealth(event.getEntityPlayer().getMaxHealth());
                    }
                }

                if (hunger)
                {
                    event.getEntityPlayer().addPotionEffect(new PotionEffect(MobEffects.hunger, hungerSeconds * 20, hungerLevel - 1));
                }
            }
        }

        @SubscribeEvent
        public void sleepEvent(PlayerSleepInBedEvent event)
        {
            if (alwaysForceSetSpawn)
            {
                ForgeEventFactory.onPlayerSpawnSet(event.getEntityPlayer(), event.getPos(), false);
            }
        }
    }

    public void configInit(File file)
    {
        config = new Configuration(file);

        try
        {
            syncConfig();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            config.save();
        }
    }

    public static void syncConfig()
    {
        config.addCustomCategoryComment(configCategory, "Toggle regeneration instead of instant health (true = on)");

        regen = config.getBoolean("regen", configCategory, true, "Regeneration = true | Instant Health = false");
        regenSeconds = config.getInt("regenSeconds", configCategory, 5, 0, 10000, "Total time (seconds) regen lasts");
        regenLevel = config.getInt("regenLevel", configCategory, 1, 1, 10, "Regeneration Level");

        hunger = config.getBoolean("hunger", configCategory, false, "Hunger when you wake up?");
        hungerSeconds = config.getInt("hungerSeconds", configCategory, 5, 0, 10000, "Total time (seconds) hunger lasts");
        hungerLevel = config.getInt("hungerLevel", configCategory, 1, 1, 10, "Hunger Level");

        alwaysForceSetSpawn = config.getBoolean("alwaysForceSetSpawn", configCategory, false, "Always force set player spawn when sleeping?");

        config.save();
    }
}
