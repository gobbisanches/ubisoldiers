package com.github.gobbisanches.ubisoldiers.app;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;

/**
 * Created by Sanches on 13/07/2014.
 */
public class UbisoldierUosApplication implements UosApplication {
    @Override
    public void start(Gateway gateway, OntologyStart ontology) {

    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void init(OntologyDeploy ontology, String appId) {

    }

    @Override
    public void tearDown(OntologyUndeploy ontology) throws Exception {

    }
}
