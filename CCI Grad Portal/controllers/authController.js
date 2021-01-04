exports.isLoggedIn = (req, res, next) => {
    if (!req.session.user) {
        req.flash('error','You must login first!');
        res.redirect("/users/login");
    } else {
       next();
    }
}

exports.isLoggedOut = (req, res, next) => {
    if (req.session.user) {
        res.redirect("/events/savedConnections");
    } else {
        next();
    }
}

exports.isIn = (req, res, next) => {
    console.log(req.params.action);
     if ((req.params.action==="Yes"|| req.params.action==="No"||req.params.action==="Maybe")) {
       next();
    } else {
        console.log('else block');        
        req.flash('error','RSVP Decision can be only Yes, No or Maybe');
        res.redirect("/events/savedConnections");
    }
}
