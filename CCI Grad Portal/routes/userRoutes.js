const express = require('express');
const router = express.Router();
const validateLogin= require('../middleware/validator').validateLogin;
const validateRegistration= require('../middleware/validator').validateRegistration;
const userController = require('../controllers/userController');
const { isLoggedIn, isLoggedOut} = require('../controllers/authController');

router.get('/signup', isLoggedOut, userController.getUserCreate);

router.post('/signup',isLoggedOut,validateRegistration,userController.postUserCreate);

router.get('/login',isLoggedOut, userController.getUserLogin);

router.post('/login',isLoggedOut, validateLogin, userController.postUserLogin);

router.get('/logout', isLoggedIn, userController.getUserLogout);

module.exports = router;