// src/middleware/authMiddleware.js
const jwt = require('jsonwebtoken');
const jwtSecret = require('../config/jwtSecret');

function verifyToken(req, res, next) {
    console.log('Executing verifyToken middleware');

    const authHeader = req.header('Authorization');

    if (!authHeader) {
        console.log('No token found');
        return res.status(403).json({
            error: true,
            message: 'Access denied. Token not found.'
        });
    }

    const token = authHeader.startsWith('Bearer ') ? authHeader.slice(7) : authHeader;

    jwt.verify(token, jwtSecret, (err, user) => {
        if (err) {
            console.error('JWT Verification Error:', err);
            return res.status(401).json({
                error: true,
                message: 'Invalid token'
            });
        }

        console.log('User from token:', user);

        if (!user || !user.user_id) {
            console.error('User not found in the token');
            return res.status(401).json({
                error: true,
                message: 'User not found'
            });
        }

        req.user = user;
        next();
    });
}

module.exports = verifyToken;