package arc.sleep;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig
{
    public ConfigGui(GuiScreen parentScreen)
    {
        super(parentScreen, getConfigElements(), Sleep.MOD_ID, false, false, "Sleep Configuration");
    }

    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<>();

        // adds sections declared in ConfigHandler. toLowerCase() is used
        // because the configuration class automatically does this, so must we.
        list.add(new ConfigElement(Sleep.getConfig().getCategory("Sleeping Configs".toLowerCase())));

        return list;
    }
}