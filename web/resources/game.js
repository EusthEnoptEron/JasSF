function update() {
    var poll = PF("poll");
    var modals = $(".modal").modal({
        backdrop: "static",
        keyboard: false
    });

    if (poll) {
        if (modals.length) {
            //console.log("stop poll");
            // We have modals, timer mustn't run
            poll.stop();
        } else {
            //console.log("start poll");
            if (!poll.isActive()) {
                poll.start()
            } else {
                //console.log("already started");
            }
        }
    } else {
        //console.log("no poll");
    }
}

$(function() {
    setTimeout(update, 100);
});