function update() {
    var poll = PF("poll");
    var modals = $(".modal").modal({
        backdrop: "static",
        keyboard: false
    });

    if (poll) {
        if (modals.length) {
            // We have modals, timer mustn't run
            poll.stop();
        } else {
            if(!poll.isActive()) {
                poll.start()
            }
        }
    }
}

$(function() {
    update();
});