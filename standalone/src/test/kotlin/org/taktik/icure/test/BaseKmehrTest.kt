package org.taktik.icure.test

import io.kotest.core.spec.style.StringSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Suppress("SpringBootApplicationProperties")
@SpringBootTest(
    classes = [KmehrTestApplication::class],
    properties = [
        "spring.main.allow-bean-definition-overriding=true",
        "icure.bridge.kmehrLogin=john",
        "icure.bridge.kmehrPwd=LetMeIn"
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = [EnvironmentBootstrapper::class])
@ActiveProfiles(profiles = ["kmehr"])
abstract class BaseKmehrTest : StringSpec()
