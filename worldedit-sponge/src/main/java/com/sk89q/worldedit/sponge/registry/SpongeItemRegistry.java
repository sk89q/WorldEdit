/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.sponge.registry;

import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.sponge.SpongeAdapter;
import com.sk89q.worldedit.sponge.SpongeTextAdapter;
import com.sk89q.worldedit.util.formatting.text.Component;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import com.sk89q.worldedit.world.item.ItemType;
import com.sk89q.worldedit.world.registry.BundledItemRegistry;
import org.spongepowered.api.data.Keys;

public class SpongeItemRegistry extends BundledItemRegistry {

    @Override
    public Component getRichName(ItemType itemType) {
        return SpongeTextAdapter.convert(SpongeAdapter.adapt(itemType).asComponent());
    }

    @Override
    public Component getRichName(BaseItemStack itemStack) {
        // return SpongeAdapter.adapt(itemStack).get(Keys.DISPLAY_NAME)
        //     .map(SpongeTextAdapter::convert)
        //     .orElseGet(() -> getRichName(itemStack.getType()));
        return TextComponent.of("// TODO");
    }
}
