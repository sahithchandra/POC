const express = require('express');
const router = express.Router();
const connectionController = require('../controllers/connectionController');
const { isLoggedIn, isLoggedOut } = require('../controllers/authController');
const validateConnection = require('../middleware/validator').validateConnection;

router.get('/getAllConnections', connectionController.getAllConnections);

router.get('/savedConnections',isLoggedIn, connectionController.getSavedConnections);

router.post('/saveConnection',validateConnection,connectionController.saveConnection);

router.get('/createConnection',isLoggedIn, connectionController.createConnection);

router.get('/:id', connectionController.getConnectionDetail);

router.get('/:id/update', isLoggedIn, connectionController.getConnectionUpdate);

router.put('/:id', isLoggedIn,validateConnection, connectionController.updateConnection);

// isIn() make sure yes no or maybe

router.delete('/:id', connectionController.deleteConnection);

module.exports = router;