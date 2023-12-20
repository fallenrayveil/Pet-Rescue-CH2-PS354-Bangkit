// src/config/storageConfig.js
const {
    Storage
} = require('@google-cloud/storage');

const storage = new Storage({
    projectId: '',
    keyFilename: '',
});

module.exports = storage;