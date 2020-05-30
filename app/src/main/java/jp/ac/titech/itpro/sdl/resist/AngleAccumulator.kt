package jp.ac.titech.itpro.sdl.resist

class AngleAccumulator(defaultAngle: Double = 0.0) {
    private var currentAngle = defaultAngle
    private var currentVelocity = 0.0
    private var lastTime = getTime()

    private fun update() {
        val now = getTime()
        val delta = currentVelocity * (now-lastTime)
        currentAngle += delta
        lastTime = now
    }

    val angle: Double get() {
        update()
        return currentAngle
    }

    fun putVelocity(velocity: Double) {
        update()
        currentVelocity = velocity
    }

    companion object {
        private fun getTime() = System.currentTimeMillis().toDouble() / 1000
    }
}