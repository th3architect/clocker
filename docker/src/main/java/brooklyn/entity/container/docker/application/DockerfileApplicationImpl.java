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
package brooklyn.entity.container.docker.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brooklyn.entity.basic.SoftwareProcessImpl;
import brooklyn.entity.container.docker.DockerContainer;
import brooklyn.entity.container.docker.DockerHost;
import brooklyn.location.docker.DockerContainerLocation;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class DockerfileApplicationImpl extends SoftwareProcessImpl implements DockerfileApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DockerfileApplication.class);

    @Override
    public void init() {
        LOG.info("Starting Dockerfile {}", getDockerfile());
        super.init();
    }

    @Override
    protected void connectSensors() {
        connectServiceUpIsRunning();
        super.connectSensors();
    }

    @Override
    protected void disconnectSensors() {
        super.disconnectSensors();
        disconnectServiceUpIsRunning();
    }

    @Override
    public void rebind() {
        super.rebind();

        disconnectSensors();
        connectSensors();
    }

    @Override
    public Class<? extends DockerfileApplicationDriver> getDriverInterface() {
        return DockerfileApplicationDriver.class;
    }

    @Override
    protected void preStart() {
        super.preStart();
    }

    @Override
    public DockerContainer getDockerContainer() {
        DockerContainerLocation location = (DockerContainerLocation) Iterables.find(getLocations(), Predicates.instanceOf(DockerContainerLocation.class));
        return location.getOwner();
    }

    @Override
    public DockerHost getDockerHost() {
        return getDockerContainer().getDockerHost();
    }

    @Override
    public String getDockerfile() {
        return getConfig(DOCKERFILE_URL);
    }

    @Override
    public List<Integer> getContainerPorts() {
        return getConfig(CONTAINER_PORT_LIST);
    }

}
