/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.command.tool;

import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extension.platform.Platform;
import com.sk89q.worldedit.util.Direction;
import com.sk89q.worldedit.util.Location;

import javax.annotation.Nullable;

public interface BlockTool extends Tool {

    /**
     * Perform the primary action of this tool.
     *
     * @param server
     * @param config
     * @param player
     * @param session
     * @param clicked
     * @return true to cancel the original event which triggered this action (if possible)
     */
    @Deprecated
    default boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, Location clicked) {
        throw new AssertionError("actPrimary must be overridden");
    }

    /**
     * Perform the primary action of this tool.
     *
     * @param server The platform
     * @param config The config instance
     * @param player The player
     * @param session The local session
     * @param clicked The location that was clicked
     * @param face The face that was clicked
     * @return true to cancel the original event which triggered this action (if possible)
     */
    default boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, Location clicked, @Nullable Direction face) {
        return actPrimary(server, config, player, session, clicked);
    }
}
