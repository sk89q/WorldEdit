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

package com.sk89q.worldedit.extent.clipboard.io.share;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.paste.PasteMetadata;

import java.net.URL;
import java.util.Set;

public interface ClipboardShareDestination {

    /**
     * Gets the name of this share destination.
     *
     * @return The name
     */
    String getName();

    /**
     * Get a set of aliases.
     *
     * @return a set of aliases
     */
    Set<String> getAliases();

    /**
     * Share a clipboard output stream and return a URL.
     *
     * @param holder The clipboard holder
     * @param format The clipboard format
     * @param metadata The clipboard metadata
     * @return The URL
     * @throws Exception if it failed to share
     */
    URL share(ClipboardHolder holder, ClipboardFormat format, PasteMetadata metadata) throws Exception;

    /**
     * Gets the default clipboard format for this share destination.
     *
     * @return The default format
     */
    ClipboardFormat getDefaultFormat();

    /**
     * Gets whether the share destination supports the given format.
     *
     * @param format The format
     * @return If it's supported
     */
    boolean supportsFormat(ClipboardFormat format);
}
