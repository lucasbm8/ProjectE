package moze_intel.projecte.impl;

import com.google.common.base.Preconditions;
import moze_intel.projecte.api.proxy.IEMCProxy;
import moze_intel.projecte.emc.mappers.APICustomEMCMapper;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.PELogger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

public class EMCProxyImpl implements IEMCProxy
{
    public static final IEMCProxy instance = new EMCProxyImpl();

    private EMCProxyImpl() {}

    @Override
    public void registerCustomEMC(ItemStack stack, int value)
    {
        Preconditions.checkNotNull(stack);
        boolean flag = Loader.instance().isInState(LoaderState.PREINITIALIZATION) || Loader.instance().isInState(LoaderState.INITIALIZATION) || Loader.instance().isInState(LoaderState.POSTINITIALIZATION);
        Preconditions.checkState(flag, String.format("Mod %s tried to register EMC at an invalid time!", Loader.instance().activeModContainer().getModId()));
        APICustomEMCMapper.instance.registerCustomEMC(stack, value);
        PELogger.logInfo("Mod %s registered emc value %d for itemstack %s", Loader.instance().activeModContainer().getModId(), value, stack.toString());
    }

    @Override
    public boolean hasValue(Block block)
    {
        Preconditions.checkNotNull(block);
        return EMCHelper.doesBlockHaveEmc(block);
    }

    @Override
    public boolean hasValue(Item item)
    {
        Preconditions.checkNotNull(item);
        return EMCHelper.doesItemHaveEmc(item);
    }

    @Override
    public boolean hasValue(ItemStack stack)
    {
        Preconditions.checkNotNull(stack);
        return hasValue(stack.getItem());
    }

    @Override
    public int getValue(Block block)
    {
        Preconditions.checkNotNull(block);
        return EMCHelper.getEmcValue(block);
    }

    @Override
    public int getValue(Item item)
    {
        Preconditions.checkNotNull(item);        
        return EMCHelper.getEmcValue(item);
    }

    @Override
    public int getValue(ItemStack stack)
    {
        Preconditions.checkNotNull(stack);
        return EMCHelper.getEmcValue(stack);
    }
}