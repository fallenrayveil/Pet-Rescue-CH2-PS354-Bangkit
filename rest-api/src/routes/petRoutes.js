// src/routes/petRoutes.js
const express = require('express');
const petController = require('../controllers/petController');
const verifyToken = require('../middleware/authMiddleware');

const router = express.Router();

router.use(express.json());

// Menampilkan daftar hewan lost
router.get('/lost', petController.getLostPets);

// Menampilkan daftar hewan found
router.get('/found', petController.getFoundPets);

// Pencarian hewan berdasarkan nama
router.get('/search/:name', petController.searchPets);

// Penampilin posting  berdasarkan user_id
router.get('/posts/:user_id', verifyToken, petController.getUserPosts);

//upload data hewan lost
router.put('/report/lost', verifyToken, petController.reportLostPet);

//upload data hewan found
router.put('/report/found', verifyToken, petController.reportFoundPet);

// Menghapus posting hewan berdasarkan ID
router.delete('/pet/:id', verifyToken, petController.deletePet);


module.exports = router;