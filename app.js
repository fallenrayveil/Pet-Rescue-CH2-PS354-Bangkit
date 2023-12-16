const express = require('express');
const multer = require('multer');
const tf = require('@tensorflow/tfjs-node');
const fs = require('fs');
const jpeg = require('jpeg-js');

const app = express();
const upload = multer({ storage: multer.memoryStorage() });

// Load the pre-trained TensorFlow.js model
let model;
tf.loadLayersModel('file://path/to/model.json').then(loadedModel => {
  model = loadedModel;
});

app.post('/predict', upload.single('file'), (req, res) => {
  // Read the image file from the request
  const imgBuffer = req.file.buffer;

  // Decode the image
  const decodedImage = jpeg.decode(imgBuffer, true);

  // Convert the image to a tensor
  const imageTensor = tf.node.decodeImage(new Uint8Array(decodedImage.data), 3);

  // Make prediction
  const prediction = model.predict(imageTensor);

  // Send the prediction as a response
  res.json({ prediction: prediction.dataSync() });
});

app.listen(3000, () => console.log('Server started on port 3000'));