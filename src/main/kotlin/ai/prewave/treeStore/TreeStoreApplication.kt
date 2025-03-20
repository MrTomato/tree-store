package ai.prewave.treeStore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("ai.prewave.treeStore.configuration")
class TreeStoreApplication

fun main(args: Array<String>) {
    runApplication<TreeStoreApplication>(*args)
}

