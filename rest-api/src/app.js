// src/app.js
const express = require('express');
const bodyParser = require('body-parser');
const authRoutes = require('./routes/authRoutes');
const petRoutes = require('./routes/petRoutes');
const PORT = process.env.PORT || 4000;

const app = express();

app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(express.json());

app.use('/auth', authRoutes);
app.use('/pet', petRoutes);



app.listen(PORT, () => {
    console.log(`Server berjalan di http://localhost:${PORT}`);
});