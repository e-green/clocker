/*
 * Copyright 2014 by Cloudsoft Corporation Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package brooklyn.location.docker.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import brooklyn.entity.Entity;
import brooklyn.location.docker.DockerHostLocation;
import brooklyn.util.collections.MutableList;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * A basic placement strategy for Docker containers, implemented as a {@link Predicate} and a {@link Comparator}.
 */
public abstract class BasicDockerPlacementStrategy extends AbstractDockerPlacementStrategy
        implements Predicate<DockerHostLocation>, Comparator<DockerHostLocation> {

    @Override
    public List<DockerHostLocation> filterLocations(List<DockerHostLocation> locations, Entity context) {
        if (locations == null || locations.isEmpty()) {
            return Lists.newArrayList();
        }

        List<DockerHostLocation> available = MutableList.copyOf(locations);
        Collections.sort(available, this);
        return MutableList.<DockerHostLocation>copyOf(Iterables.filter(available, this));
    }

    /**
     * A {@link Predicate} function that selects Docker hosts that satisy the strategy requirements.
     */
    @Override
    public abstract boolean apply(DockerHostLocation input);

    /**
     * A {@link Comparator} function that orders Docker hosts according to suitability, best first.
     */
    @Override
    public abstract int compare(DockerHostLocation l1, DockerHostLocation l2);

}
