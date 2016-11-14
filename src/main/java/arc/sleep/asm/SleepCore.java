package arc.sleep.asm;

import arc.sleep.Sleep;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class SleepCore extends DummyModContainer
{
    public SleepCore()
    {
        super(new ModMetadata());

        ModMetadata metadata = getMetadata();
        metadata.modId = Sleep.MODID + "Core";
        metadata.version = Sleep.VERSION;
        metadata.name = "SleepCore";
        metadata.authorList.add("Arcaratus");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}
