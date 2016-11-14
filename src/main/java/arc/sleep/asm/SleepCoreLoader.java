package arc.sleep.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.9")
@IFMLLoadingPlugin.Name(SleepCoreLoader.NAME)
public class SleepCoreLoader implements IFMLLoadingPlugin
{
    public static final String NAME = "SleepCore";

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{PatchCollection.class.getName()};
    }

    @Override
    public String getModContainerClass()
    {
        return SleepCore.class.getName();
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
