const User = require('../models/user');
const validationResult= require('express-validator').validationResult;

exports.getUserCreate = (req, res,next) => {
    res.render('./users/signup');
}   

exports.postUserCreate = (req, res, next) => {
    const errors= validationResult(req);
    console.log(errors.array());
    if(!errors.isEmpty()){
        console.log('errors');
        errors.array().forEach((error)=>{
            req.flash('error',error.msg);
        });
        res.redirect('/');
    }
    else{
    let user = new User({
        firstName: req.body.firstName,
        lastName: req.body.lastName,
        email: req.body.email,
        password: req.body.password
    });
    user.save()
        .then(result => {
            res.redirect('/');
        })
        .catch(err => {
            console.log(err);
            next();
        });
    }
}

exports.getUserLogin = (req, res, next) => {
    res.render('./users/login');
}

exports.postUserLogin = (req, res, next) => {
    const errors= validationResult(req);
    console.log(errors.array());
    if(!errors.isEmpty()){
        errors.array().forEach((error)=>{
        req.flash('error',error.msg);
        })
        res.redirect('/users/login');
    }
    else{
    let email = req.body.email;
    let password = req.body.password;
    User.findOne({ email: email })
    .populate('user','firstName') 
        .then(user => {
            if (user) {
                user.comparePassword(password)
                    .then(isMatch => {
                        if (isMatch) {
                            req.session.user = { id: user._id, name: user.firstName };
                            req.flash('success','Successfully logged in');
                            res.redirect('/');
                        } else {
                            req.flash('error','Incorrect password!! Please try again');
                            res.redirect('/users/login');
                        }
                    })
            } else {          
                req.flash('error','Incorrect email address!! Please try again');
                res.redirect('/users/login');
            }
        })
        .catch(err => {
            console.log(err);
            next();
        });
    }
}

exports.getUserLogout = (req, res, next) => {
    req.session.destroy(err => {
        res.redirect('/');
    });
}