// src/routes/authRoutes.js
const express = require('express');
const authController = require('../controllers/authController');

const router = express.Router();

router.use(express.json());

router.post('/register', authController.postRegister);
router.post('/login', authController.postLogin);
router.get('/profile', authController.getProfile);



module.exports = router;