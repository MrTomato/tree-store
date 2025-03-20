package ai.prewave.treeStore

import io.kotest.core.spec.style.FreeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class TreeStoreApplicationTests : FreeSpec({
    "Context loads" {
        println("✅ Context loads")
    }
})