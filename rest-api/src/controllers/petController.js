// src/controllers/petController.js
const express = require('express');
const multer = require('multer');
const storage = require('../config/storageConfig');
const db = require('../src/config/dbConfig');
const verifyToken = require('../middleware/authMiddleware');
const {
    route
} = require('./authController');

const router = express.Router();

// Menampilkan daftar hewan lost
router.get('/lost', (req, res) => {
    const getLostPetsQuery = 'SELECT * FROM pets WHERE detail = "lost"';
    db.query(getLostPetsQuery, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({
                error: true,
                message: 'there is an error'
            });
        }
        res.json(results);
    });
});

// Menampilkan daftar hewan found
router.get('/found', (req, res) => {
    const getFoundPetsQuery = 'SELECT * FROM pets WHERE detail = "found"';
    db.query(getFoundPetsQuery, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({
                error: true,
                message: 'there is an error'
            });
        }
        res.json(results);
    });
});

// Pencarian hewan berdasarkan nama
router.get('/search/:name', (req, res) => {
    const petName = req.params.name;
    const searchPetsQuery = 'SELECT * FROM pets WHERE LOWER(pet_name) LIKE LOWER(?)';
    db.query(searchPetsQuery, [`%${petName}%`], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({
                error: true,
                message: 'There is an error'
            });
        }
        res.json(results);
    });
});


// Menampilkan posting berdasarkan user_id
router.get('/posts/:user_id', verifyToken, (req, res) => {
    const userId = req.params.user_id;

    // Gantilah query di bawah ini sesuai dengan kebutuhan Anda
    const getUserPostsQuery = 'SELECT * FROM pets WHERE user_id = ?';

    db.query(getUserPostsQuery, [userId], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({
                error: true,
                message: 'There is an error'
            });
        }

        if (results.length === 0) {
            return res.status(404).json({
                error: true,
                message: 'No posts found for the user'
            });
        }

        res.json(results);
    });
});


router.put('/report/lost', verifyToken, async (req, res) => {
    try {
        console.log('Request Body:', req.body);

        const {
            user_id,
            pet_id, // Extract pet_id from the request body
            pet_name,
            gender,
            reward,
            province,
            regency,
            found_area,
            email,
            phone_number,
            detail,
        } = req.body;

        // Dapatkan user_id berdasarkan email dari token JWT
        const userId = req.user.user_id;

        // Lakukan pembaruan entri di database
        const updateLostPetQuery = `
            UPDATE pets
            SET
                user_id = ?,
                pet_name = ?,
                gender = ?,
                reward = ?,
                province = ?,
                regency = ?,
                found_area = ?,
                email = ?,
                phone_number = ?,
                detail = ?
            WHERE pet_id = ?;
        `;

        const updateResult = await db.promise().execute(updateLostPetQuery, [
            user_id,
            pet_name,
            gender,
            reward,
            province,
            regency,
            found_area,
            email,
            phone_number,
            detail,
            pet_id,
        ]);

        console.log('Update result:', updateResult);

        res.status(200).json({
            error: false,
            message: 'Hewan Hilang Berhasil Di Upload'
        });
    } catch (error) {
        console.error('Error processing report/lost:', error);
        return res.status(500).json({
            error: true,
            message: 'Error processing report/lost. Lihat log server untuk rincian.',
        });
    }
});


router.put('/report/found', verifyToken, async (req, res) => {
    try {
        console.log('Request Body:', req.body);

        const {
            user_id,
            pet_id, // Extract pet_id from the request body
            pet_name,
            gender,
            reward,
            province,
            regency,
            found_area,
            email,
            phone_number,
            detail,
        } = req.body;

        // Dapatkan user_id berdasarkan email dari token JWT
        const userId = req.user.user_id;

        // Lakukan pembaruan entri di database
        const updateLostPetQuery = `
            UPDATE pets
            SET
                user_id = ?,
                pet_name = ?,
                gender = ?,
                reward = ?,
                province = ?,
                regency = ?,
                found_area = ?,
                email = ?,
                phone_number = ?,
                detail = ?
            WHERE pet_id = ?;
        `;

        const updateResult = await db.promise().execute(updateLostPetQuery, [
            user_id,
            pet_name,
            gender,
            reward,
            province,
            regency,
            found_area,
            email,
            phone_number,
            detail,
            pet_id,
        ]);

        console.log('Update result:', updateResult);

        res.status(200).json({
            error: false,
            message: 'Hewan Ditemukan  Berhasil Di Upload'
        });
    } catch (error) {
        console.error('Error processing report/lost:', error);
        return res.status(500).json({
            error: true,
            message: 'Error processing report/lost. Lihat log server untuk rincian.',
        });
    }
});


// Menghapus posting hewan berdasarkan ID
router.delete('/pet/:id', verifyToken, (req, res) => {
    const petId = req.params.id;
    const deletePetQuery = 'DELETE FROM pets WHERE pet_id = ?';
    db.query(deletePetQuery, [petId], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({
                error: true,
                message: 'there is an error'
            });
        }
        if (results.affectedRows === 0) {
            return res.status(404).json({
                error: true,
                message: 'Pet not found'
            });
        }
        res.json({
            error: false,
            message: 'Pet deleted successfully'
        });
    });
});


// ... (rute tambahan)

module.exports = router;