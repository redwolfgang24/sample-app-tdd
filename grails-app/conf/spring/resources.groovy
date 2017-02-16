// Place your Spring DSL code here

import com.sampleapptdd.demo.bootstrap.BootstrapInitializer
import com.sampleapptdd.demo.bootstrap.init.DataInitializer
import com.sampleapptdd.demo.marshalling.CustomObjectMarshallers
import com.sampleapptdd.demo.marshalling.MarshallerInitializer
import com.sampleapptdd.demo.marshalling.marshaller.AuthTokenMarshaller
import com.sampleapptdd.demo.marshalling.marshaller.UserMarshaller
import com.sampleapptdd.demo.security.CustomRestAuthenticationProvider
import com.sampleapptdd.demo.security.CustomTokenStorageService
import com.sampleapptdd.demo.security.RestAccessDeniedHandler
import com.sampleapptdd.demo.security.RestUnauthorizedFailureHandler
import com.sampleapptdd.demo.token.generator.ConfigurableSecureRandomTokenGenerator
import com.sampleapptdd.demo.token.generator.SecureRandomTokenGenerator
import com.sampleapptdd.demo.token.generator.ShortSecureRandomTokenGenerator
import com.sampleapptdd.demo.token.generator.VerificationRandomTokenGenerator
import com.sampleapptdd.demo.util.DateTimeGenerator
import groovy.text.SimpleTemplateEngine
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.jms.connection.SingleConnectionFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.apache.activemq.ActiveMQConnectionFactory
import grails.plugins.rest.client.RestBuilder

beans = {

    authTokenBcrypt(BCrypt)

    unauthorizedFailureHandler(RestUnauthorizedFailureHandler)

    tokenStorageService(CustomTokenStorageService)

    restAuthenticationProvider(CustomRestAuthenticationProvider) {
        tokenStorageService = ref('tokenStorageService')
        userRoleService = ref('userRoleService')
    }

    restAccessDeniedHandler(RestAccessDeniedHandler)

    secureRandomTokenGenerator(SecureRandomTokenGenerator)
    shortTokenGenerator(ShortSecureRandomTokenGenerator)
    configurableSecureTokenGenerator(ConfigurableSecureRandomTokenGenerator)
    verificationRandomTokenGenerator(VerificationRandomTokenGenerator)

    restBuilder(RestBuilder)

    dateTimeGenerator(DateTimeGenerator)

    simpleTemplateEngine(SimpleTemplateEngine)

    customObjectMarshallers(CustomObjectMarshallers) {
        marshallers = [
                new UserMarshaller(),
                new AuthTokenMarshaller(),
        ]
    }

    marshallerInitializer(MarshallerInitializer) {
        customObjectMarshallers = ref('customObjectMarshallers')
    }

    dataInitializer(DataInitializer)

    bootstrapInitializer(BootstrapInitializer){
        initializers = [ref('marshallerInitializer'), ref('dataInitializer')]
    }

    jmsConnectionFactory(SingleConnectionFactory){
        targetConnectionFactory = { ActiveMQConnectionFactory cf -> brokerURL = grailsApplication.config.brokerUrl }
    }

    conversionService(ConversionServiceFactoryBean)
}
