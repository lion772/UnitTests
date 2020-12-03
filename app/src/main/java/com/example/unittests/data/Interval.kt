class Interval(start: Int, end: Int) {
    var start: Int = 0
    var end: Int = 0

    init {
        require(start < end) { "invalid interval range" }
        this.start = start
        this.end = end
    }
}