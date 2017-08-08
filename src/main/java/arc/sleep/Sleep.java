package arc.sleep;

import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod(modid = Sleep.MOD_ID, version = Sleep.VERSION, name = "Sleep", guiFactory = "arc.sleep.ConfigGuiFactory")
public class Sleep
{
    public final static String MOD_ID = "sleep";
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
        configInit(new File(event.getModConfigurationDirectory(), "sleep"));
        MinecraftForge.EVENT_BUS.register(new SleepEvent());
    }

    public class SleepEvent
    {
        @SubscribeEvent
        public void wakeUpEvent(PlayerWakeUpEvent event)
        {
            if (event.getEntityPlayer() != null)
            {
                if (event.getEntityPlayer().getEntityWorld().getWorldTime() == 24000)
                {
                    if (event.getEntityPlayer().getHealth() < event.getEntityPlayer().getMaxHealth())
                    {
                        if (regen)
                        {
                            event.getEntityPlayer().addPotionEffect(new PotionEffect(MobEffects.REGENERATION, regenSeconds * 20, regenLevel));
                        }
                        else
                        {
                            event.getEntityPlayer().setHealth(event.getEntityPlayer().getMaxHealth());
                        }
                    }

                    if (hunger)
                    {
                        event.getEntityPlayer().addPotionEffect(new PotionEffect(MobEffects.HUNGER, hungerSeconds * 20, hungerLevel));
                    }
                }
            }
        }

        @SubscribeEvent
        public void rightClickOnBlockEvent(PlayerInteractEvent.RightClickBlock event)
        {
            if (alwaysForceSetSpawn)
            {
                if (event.getEntityPlayer().getEntityWorld().getBlockState(event.getPos()).getBlock() instanceof BlockBed)
                {
                    System.out.println("ITS A BED");
                    event.getEntityPlayer().setSpawnPoint(event.getPos(), true);

                    sendNoSpamMessages(new TextComponentString(I18n.format("chat.sleep.forcedSetSpawn")));
                }
            }
        }
    }

    private static final int DELETION_ID = 2525277;
    private static int lastAdded;

    private static void sendNoSpamMessages(ITextComponent... messages)
    {
        GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        for (int i = DELETION_ID + messages.length - 1; i <= lastAdded; i++)
        {
            chat.deleteChatLine(i);
        }
        for (int i = 0; i < messages.length; i++)
        {
            chat.printChatMessageWithOptionalDeletion(messages[i], DELETION_ID + i);
        }
        lastAdded = DELETION_ID + messages.length - 1;
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
        config.addCustomCategoryComment(configCategory, "Various configs for sleeping");

        regen = config.getBoolean("regen", configCategory, true, "Regeneration = true | Instant Health = false");
        regenSeconds = config.getInt("regenSeconds", configCategory, 5, 0, 10000, "Total time (seconds) regen lasts");
        regenLevel = config.getInt("regenLevel", configCategory, 0, 0, 9, "Regeneration Level");

        hunger = config.getBoolean("hunger", configCategory, false, "Hunger when you wake up?");
        hungerSeconds = config.getInt("hungerSeconds", configCategory, 5, 0, 10000, "Total time (seconds) hunger lasts");
        hungerLevel = config.getInt("hungerLevel", configCategory, 0, 0, 9, "Hunger Level");

        alwaysForceSetSpawn = config.getBoolean("alwaysForceSetSpawn", configCategory, false, "Always force set player spawn when right-click on bed?");

        config.save();
    }
}
