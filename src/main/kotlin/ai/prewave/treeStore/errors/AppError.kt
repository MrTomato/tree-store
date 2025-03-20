package ai.prewave.treeStore.errors

interface AppError {
    val error: String
    val reason: String?
}