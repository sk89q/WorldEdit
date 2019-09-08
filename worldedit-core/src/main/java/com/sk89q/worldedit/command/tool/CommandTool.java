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
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extension.platform.Platform;
import com.sk89q.worldedit.util.Location;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public class CommandTool implements BlockTool {

    private final String filename;
    private final File file;
    private final List<String> args;

    public CommandTool(String filename, File file, List<String> args) {
        this.filename = filename;
        this.file = file;
        this.args = args;
    }

    @Override
    public boolean canUse(Actor player) {
        return player.hasPermission("worldedit.tool.command")
            && player.hasPermission("worldedit.scripting.execute")
            && player.hasPermission("worldedit.scripting.execute." + filename);
    }

    @Override
    public boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, Location clicked) {
        try {
            WorldEdit.getInstance().runScript(player, file, Stream.concat(Stream.of(filename), args.stream())
                .toArray(String[]::new));
        } catch (WorldEditException e) {
            return false;
        }
        return true;
    }
}
