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
        super(parentScreen, getConfigElements(parentScreen), Sleep.MODID, false, false, "Sleep Configuration");
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements(GuiScreen parent)
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        // adds sections declared in ConfigHandler. toLowerCase() is used
        // because the configuration class automatically does this, so must we.
        list.add(new ConfigElement(Sleep.getConfig().getCategory("Sleeping Configs".toLowerCase())));

        return list;
    }
}