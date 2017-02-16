import com.sampleapptdd.demo.bootstrap.BootstrapInitializer

class BootStrap {

    BootstrapInitializer bootstrapInitializer

    def init = { servletContext ->
        environments {
            development {
                bootstrapInitializer.execute()
            }
            staging {
                bootstrapInitializer.execute()
            }
            production {
                bootstrapInitializer.execute()
            }
        }
    }

    def destroy = {
    }
}
