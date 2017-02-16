grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        mavenRepo 'http://repo.spring.io/milestone'
        mavenRepo 'http://mvnrepository.com'
        mavenRepo 'https://repo1.maven.org/maven2/'
    }

    dependencies {
        build 'org.apache.httpcomponents:httpcore:4.3.2'
        build 'org.apache.httpcomponents:httpclient:4.3.2'
        runtime 'org.apache.httpcomponents:httpcore:4.3.2'
        runtime 'org.apache.httpcomponents:httpclient:4.3.2'
        runtime 'com.github.groovy-wslite:groovy-wslite:1.1.0'
        //runtime 'org.postgresql:postgresql:9.4.1208.jre7'
        runtime 'org.postgresql:postgresql:9.4-1206-jdbc41'
        //runtime 'org.postgresql:postgresql:9.3-1100-jdbc4'

        compile 'org.elasticmq:elasticmq-rest-sqs_2.11:0.8.12'
        compile "org.jadira.usertype:usertype.jodatime:2.0.1"
        compile 'com.google.guava:guava:17.0'
        compile 'dnsjava:dnsjava:2.1.7'
        compile 'jmimemagic:jmimemagic:0.1.2'
        compile 'com.twelvemonkeys.imageio:imageio-ico:3.0.2'
        compile 'org.im4java:im4java:1.4.0'
        compile 'org.imgscalr:imgscalr-lib:4.2'
        compile 'org.flywaydb:flyway-core:3.0'
        compile 'org.rythmengine:rythm-engine:1.0.1'
        compile 'org.jsoup:jsoup:1.8.1'
        compile 'org.codehaus.groovy.modules.http-builder:http-builder:0.7.1'
        compile "com.pusher:pusher-http-java:1.0.0"
        compile 'com.cedarsoftware:json-io:2.7.2'   // json-io for resolvable notification serialization
        compile 'org.apache.ws.security:wss4j:1.6.17'
        compile 'org.apache.cxf:cxf-rt-ws-security:2.6.2'
        compile 'org.apache.activemq:activemq-core:5.7.0'
        compile 'org.apache.commons:commons-csv:1.2'
        compile 'org.apache.tika:tika-core:1.12'
        compile "org.projectlombok:lombok:1.16.2"
        compile 'commons-io:commons-io:2.4'
        compile 'commons-fileupload:commons-fileupload:1.3'
        compile 'org.codehaus.gpars:gpars:1.2.1'
        compile 'com.itextpdf.tool:xmlworker:5.5.8'
        compile "jmimemagic:jmimemagic:0.1.2"
    }

    plugins {
        // plugins for the build system only
        //build ":tomcat:7.0.55"
        build ':tomcat:8.0.30'
        compile ":aws-sdk:1.10.44"
        //compile ':aws-sdk:1.7.7'

        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        //compile ':asset-pipeline:1.9.9'
        compile ':asset-pipeline:2.1.5'
        //compile ':asset-pipeline:2.7.2'
        compile ":mail:1.0.7"

        // plugins needed at runtime but not for compilation
        runtime ':hibernate:3.6.10.19' // or ':hibernate4:4.3.6.1'
        runtime ":database-migration:1.4.0"
        runtime ':jquery:1.11.0.2'

        //test data builder plugin
        compile ':build-test-data:2.4.0'

        // test hibernate criteria
        //test ":plastic-criteria:1.5.1"

        compile ":spring-security-core:2.0-RC3"
        compile ":spring-security-rest:1.4.1.RC1", {
            excludes: 'spring-security-core'
        }

        compile ':gflyway2:0.4.1'

        compile ':functional-test-development:0.9.3'
        compile ':quartz:1.0.1'
        compile ":platform-core:1.0.0"
        compile ":jms:1.3-SNAPSHOT"
        compile ":remote-control:1.5"
        compile ":rest-client-builder:2.1.1"
        compile ":joda-time:1.5"
    }
}
