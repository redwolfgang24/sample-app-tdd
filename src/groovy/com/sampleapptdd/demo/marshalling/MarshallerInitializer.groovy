package com.sampleapptdd.demo.marshalling

import com.sampleapptdd.demo.bootstrap.init.BootstrapInitializerComponent

class MarshallerInitializer implements BootstrapInitializerComponent {

    CustomObjectMarshallers customObjectMarshallers

    @Override
    void initialize() {
        customObjectMarshallers.register()
    }
}
