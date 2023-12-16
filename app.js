process.env['TF_CPP_MIN_LOG_LEVEL'] = '2'; // 0 = default, 1 = info, 2 = warning, 3 = error
const express = require('express');
const multer = require('multer');
const tf = require('@tensorflow/tfjs-node');
const fs = require('fs');
const jpeg = require('jpeg-js');
const {Storage} = require('@google-cloud/storage');

const app = express();
const upload = multer({ storage: multer.memoryStorage() });

// Creates a client from a Google service account key.
const storage = new Storage({keyFilename: "./config/pet-rescue-407209-74395c7dca01.json"});

async function downloadFile() {
    const options = {
      // The path to which the file should be downloaded, e.g. "./file.txt"
      destination: './uploads/inceptionv3.tflite',
    };
  
    // Downloads the file
    await storage
      .bucket('tflite-data')
      .file('inceptionv3.tflite')
      .download(options);
  
    // console.log(
    //   `gs://${bucketName}/${srcFilename} downloaded to ${destFilename}.`
    // );
  }
  
//   downloadFile().catch(console.error);

  app.get('/download', async (req, res) => {
downloadFile().catch(console.error); 
});

// Load the pre-trained TensorFlow.js model
// let model;
// tf.loadLayersModel('file://path/to/model.json').then(loadedModel => {
//   model = loadedModel;
// });

// app.post('/predict', upload.single('file'), (req, res) => {
//   // Read the image file from the request
//   const imgBuffer = req.file.buffer;

//   // Decode the image
//   const decodedImage = jpeg.decode(imgBuffer, true);

//   // Convert the image to a tensor
//   const imageTensor = tf.node.decodeImage(new Uint8Array(decodedImage.data), 3);

//   // Make prediction
//   const prediction = model.predict(imageTensor);

//   // Send the prediction as a response
//   res.json({ prediction: prediction.dataSync() });
// });

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});