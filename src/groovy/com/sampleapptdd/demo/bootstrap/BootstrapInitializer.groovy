package com.sampleapptdd.demo.bootstrap

import com.sampleapptdd.demo.bootstrap.init.BootstrapInitializerComponent

class BootstrapInitializer {

    List<BootstrapInitializerComponent> initializers

    public void execute() {
        initializers.each { initializer ->
            initializer.initialize()
        }
    }
}
