package use_cases

object Retry {
    inline fun <T> retry(times: Int, exception: Class<out RuntimeException>, delay: Long = 100L, block: () -> T): T {
        var ex: Throwable? = null
        repeat(times) {
            try {
                return block()
            } catch (exc: RuntimeException) {
                if (!exception.isAssignableFrom(exc.javaClass)) {
                    throw exc
                }
                Thread.sleep(delay)
                ex = exc
            }
        }
        throw ex!!
    }
}