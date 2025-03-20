package ai.prewave.treeStore

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<TreeStoreApplication>().with(TestcontainersConfiguration::class).run(*args)
}
