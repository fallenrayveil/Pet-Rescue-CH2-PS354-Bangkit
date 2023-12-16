const axios = require('axios');
const fs = require('fs');

// Use the URL of your deployed application
let url = 'http://localhost:3000/predict';

// Open your image file in binary mode
let img_file = fs.createReadStream('test_image.jpg');

// Make a POST request to the server
axios({
  method: 'post',
  url: url,
  data: img_file,
  headers: { 'Content-Type': 'application/octet-stream' }
})
.then(function (response) {
  // The response will contain the prediction
  console.log(response.data);
})
.catch(function (error) {
  console.log(error);
});