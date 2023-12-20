// src/config/dbConfig.js
const mysql = require('mysql2');
require('dotenv').config();

const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE,
    authPlugins: {
        mysql_clear_password: () => () => Buffer.from(process.env.DB_PASSWORD + '\0'),
    },
});

db.connect((err) => {
    if (err) {
        console.error('Koneksi ke MySQL gagal: ', err);
    } else {
        console.log('Terhubung ke MySQL');
        console.log('Host Cloud SQL: ', process.env.DB_HOST);
        console.log('Nama Database: ', process.env.DB_DATABASE);
    }
});

module.exports = db;