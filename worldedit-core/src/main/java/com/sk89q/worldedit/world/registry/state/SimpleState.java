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

package com.sk89q.worldedit.world.registry.state;

import com.sk89q.worldedit.world.registry.state.value.SimpleStateValue;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class SimpleState<T extends SimpleStateValue> implements State<T> {

    private String name;
    private List<T> values;

    /**
     * Creates a state with values
     *
     * @param values The values
     */
    public SimpleState(List<T> values) {
        this.name = "Unknown";
        this.values = values;
    }

    /**
     * Internal method for name setting post-deserialise. Do not use.
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public List<T> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Nullable
    @Override
    public T getValueFor(String string) {
        return values.stream().filter(value -> Objects.equals(value.getData(), string)).findFirst().orElse(null);
    }
}