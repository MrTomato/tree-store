package ai.prewave.treeStore.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tree-store")
data class TreeStoreConfig(
    val defaultMaxDepth: Int,
)