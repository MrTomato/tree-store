package ai.prewave.treeStore.errors

data class DatabaseError(
    override val error: String,
    override val reason: String? = null
) : AppError
