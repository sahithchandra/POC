const check= require('express-validator').check;

exports.validateConnection= [
    check('connectionName','Your connectionName must contain atleast 3 characters').trim().isLength({min:3}),
    check('connectionTopic','your connectionTopic must contain only characters').notEmpty().isAlpha().trim(),
    check('details','Your details must be non empty').notEmpty().trim(),
    check('date','Your date must be non empty').notEmpty().trim(),
    check('startingTime','Your startingTime must be non empty').notEmpty().trim(),
    check('endingTime','Your endingTime must be non empty').notEmpty().trim(),
    check('hostName','Your hostName must be non empty').notEmpty().trim(),
    check('image','Your image must must be non empty').notEmpty().trim(),
    check('location','Your location must be non empty').notEmpty().trim()
];

exports.validateLogin= [
    check('email','your email is not valid').notEmpty().isEmail().trim().normalizeEmail(),
    check('password','Your password must contain atleast 5 characters').trim().isLength({min:5})
];

exports.validateRegistration= [
    check('email','your email is not valid').notEmpty().isEmail().trim().normalizeEmail(),
    check('password','Your password must contain atleast 5 characters').trim().isLength({min:5}),
    check('firstName','Your firstName must contain only characters').notEmpty().isAlpha().trim(),
    check('lastName','Your lastName must contain only characters').notEmpty().isAlpha().trim()
];
