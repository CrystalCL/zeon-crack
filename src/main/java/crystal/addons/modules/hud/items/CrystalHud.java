package crystal.addons.modules.hud.items;

import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.render.hud.HUD;
import meteordevelopment.meteorclient.systems.modules.render.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.render.hud.modules.HudElement;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CrystalHud extends HudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> scale = sgGeneral.add((((new DoubleSetting.Builder()).name("scale")).description("The scale.")).defaultValue(2.0D).min(1.0D).sliderMin(1.0D).sliderMax(5.0D).build());

    public CrystalHud(HUD hud) {
        super(hud, "crystals", "Displays the amount of crystals in your inventory.", false);
    }

    public void update(HudRenderer renderer) {
        box.setSize(16.0D * scale.get(), 16.0D * scale.get());
    }

    public void render(HudRenderer renderer) {
        double x = box.getX();
        double y = box.getY();
        if (isInEditor()) {
            RenderUtils.drawItem(Items.END_CRYSTAL.getDefaultStack(), (int) x, (int) y, scale.get(), true);
        } else if (InvUtils.find(new Item[]{Items.END_CRYSTAL}).getCount() > 0) {
            RenderUtils.drawItem(new ItemStack(Items.END_CRYSTAL, InvUtils.find(new Item[]{Items.END_CRYSTAL}).getCount()), (int) x, (int) y, scale.get(), true);
        }
    }
}
